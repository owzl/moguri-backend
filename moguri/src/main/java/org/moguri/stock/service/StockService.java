
package org.moguri.stock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.PageRequest;
import org.moguri.exception.MoguriRequestException;
import org.moguri.member.service.MemberService;
import org.moguri.stock.domain.*;
import org.moguri.stock.enums.ApiEndPoint;
import org.moguri.stock.enums.Period;
import org.moguri.stock.param.StockTradeParam;
import org.moguri.stock.repository.StockMapper;
import org.moguri.stock.stockResponse.Output;
import org.moguri.stock.stockResponse.PriceResponse;
import org.moguri.stock.stockResponse.StockChart;
import org.moguri.stock.stockResponse.StockToken;
import org.moguri.stock.param.TokenParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class StockService {

    private final StockMapper stockMapper;

    private final RestTemplate restTemplate;

    private final RedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    private final MemberService memberService;

    private String divCode;

    @Value("${api.appKey}")
    private String appKey;
    @Value("${api.appSecret}")
    private String appSecret;
    @Value("${api.grantType}")
    private String grantType;
    @Value("${api.baseUrl}")
    private String baseUrl;

    private final String mktCode = "J";

    public String getToken() {
        // Redis에서 token 값 가져오기
        Object token = redisTemplate.opsForValue().get(ApiTokenConst.AccessToken);

        // Redis에 token 값이 없다면 새로운 토큰을 생성
        if (token == null) {
            String url = baseUrl + ApiEndPoint.GET_TOKEN.getPath();

            TokenParam param = TokenParam.builder()
                    .grant_type(grantType)
                    .appkey(appKey)
                    .appsecret(appSecret)
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<TokenParam> entity = new HttpEntity<>(param, headers);
            ResponseEntity<StockToken> response = restTemplate.postForEntity(url, entity, StockToken.class);
            String accessToken = response.getBody().getAccess_token();

            // Redis에 새로 받은 토큰과 TTL을 24시간으로 설정
            redisTemplate.opsForValue().set(ApiTokenConst.AccessToken, accessToken, 24, TimeUnit.HOURS);

            return accessToken;
        }

        // Redis에 token 값이 존재하면 그 값을 반환
        return (String) token;
    }

    public Output getPresentPrice(String stockCode) throws JsonProcessingException {
        String cachedOutputJson = (String) redisTemplate.opsForValue().get(stockCode);  // JSON 문자열로 가져오기
        if (cachedOutputJson != null) {
            return objectMapper.readValue(cachedOutputJson, Output.class);
        }

        String accessToken;
        // Redis에서 token 값 가져오기
        accessToken = (String) redisTemplate.opsForValue().get(ApiTokenConst.AccessToken);

        // Redis에 token 값이 없다면 새로운 토큰을 생성
        if (accessToken == null) {
            accessToken = getToken();
        }
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + ApiEndPoint.GET_PRESENT_PRICE.getPath())
                .queryParam("FID_COND_MRKT_DIV_CODE", mktCode)
                .queryParam("FID_INPUT_ISCD", stockCode)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("appkey", appKey);
        headers.set("appsecret", appSecret);
        headers.set("tr_id", "FHKST01010100");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<PriceResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, PriceResponse.class);
        Output output = response.getBody().getOutput();
        String outputJson = objectMapper.writeValueAsString(output);  // Output 객체를 JSON 문자열로 변환
        redisTemplate.opsForValue().set(stockCode, outputJson, 5, TimeUnit.SECONDS);  // stockCode를 key로 JSON 문자열 저장
        return output;
    }

    public List<StockChart> getStockChart(String stockCode, Period period) throws JsonProcessingException {
        String accessToken;
        // Redis에서 token 값 가져오기
        accessToken = (String) redisTemplate.opsForValue().get(ApiTokenConst.AccessToken);

        // Redis에 token 값이 없다면 새로운 토큰을 생성
        if (accessToken == null) {
            accessToken = getToken();
        }

        List<StockChart> chartStockDataList = new ArrayList<>();
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/json");
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("appkey", appKey);
        headers.set("appsecret", appSecret);
        headers.set("tr_id", "FHKST03010100");

        for (int i = 0; i < 3; i++) {
            LocalDate startDate;
            switch (period) {
                case DAY:
                    divCode = "D";
                    startDate = now.minusDays(100); // 100일 전
                    break;
                case WEEK:
                    divCode = "W";
                    startDate = now.minusWeeks(100); // 100주 전
                    break;
                case MONTH:
                    divCode = "M";
                    startDate = now.minusMonths(100); // 100달 전
                    break;
                case YEAR:
                    divCode = "Y";
                    startDate = now.minusYears(100); // 100년 전
                    break;
                default:
                    throw new MoguriRequestException(ReturnCode.INVALID_PERIOD_TYPE);
            }

            // startDate 포맷팅
            String formattedStartDate = startDate.format(formatter);
            String formattedEndDate = now.format(formatter);

            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + ApiEndPoint.GET_PRESENT_PRICE.getPath())
                    .queryParam("FID_COND_MRKT_DIV_CODE", mktCode)
                    .queryParam("FID_INPUT_ISCD", stockCode)
                    .queryParam("FID_INPUT_DATE_1", formattedStartDate)
                    .queryParam("FID_INPUT_DATE_2", formattedEndDate)
                    .queryParam("FID_PERIOD_DIV_CODE", divCode)
                    .queryParam("FID_ORG_ADJ_PRC", "0")
                    .toUriString();

            now = startDate.minusDays(1);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            String responseBody = response.getBody();
            Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);

            // output2 리스트 추출
            List<Map<String, Object>> output2 = (List<Map<String, Object>>) responseMap.get("output2");

            // 필요한 데이터를 담을 List 생성
            for (Map<String, Object> stock : output2) {
                String stckBsopDate = (String) stock.get("stck_bsop_date");
                String stckClpr = (String) stock.get("stck_clpr");
                String stckOprc = (String) stock.get("stck_oprc");
                String stckHgpr = (String) stock.get("stck_hgpr");
                String stckLwpr = (String) stock.get("stck_lwpr");
                String acmlVol = (String) stock.get("acml_vol");

                StockChart chart = StockChart.builder()
                        .stckBsopDate(stckBsopDate)
                        .stckClpr(stckClpr)
                        .stckOprc(stckOprc)
                        .stckHgpr(stckHgpr)
                        .stckLwpr(stckLwpr)
                        .acmlVol(acmlVol)
                        .build();
                chartStockDataList.add(chart);
            }
        }
        return chartStockDataList;
    }

    public List<Stock> findStockByKeyword(PageRequest pageRequest, String keyword) {
        return stockMapper.findStockByKeyword(pageRequest, keyword);
    }

    public void tradeStock(StockTradeParam param) {
        StockTrade tradeHistory = param.toEntity();
        stockMapper.saveTrade(tradeHistory);

        Long memberId = param.getMemberId();
        int totalAmount = param.getTotalAmount();
        int cottonCandy = memberService.getCottonCandy(memberId);
        switch (param.getTradeType()) {
            case BUY:
                // 매수 관련 로직
                int remainingCottonCandy = cottonCandy - totalAmount;
                if (remainingCottonCandy < 0) {
                    throw new MoguriRequestException(ReturnCode.NOT_ENOUGH_COTTON_CANDY);
                }
                memberService.updateCottonCandy(memberId, remainingCottonCandy);
                break;
            case SELL:
                // 매도 관련 로직
                int remainingQuantity = stockMapper.findRemainingQuantity(memberId, param.getStockCode());
                if (remainingQuantity - param.getQuantity() < 0) {
                    throw new MoguriRequestException(ReturnCode.NOT_ENOUGH_STOCKS);
                }
                memberService.updateCottonCandy(memberId, cottonCandy + totalAmount);
                break;
            default:
                throw new MoguriRequestException(ReturnCode.INVALID_TRADE_TYPE);
        }
    }

    public List<TradeHistory> getTradeHistory(PageRequest pageRequest, Long memberId, String stockCode) {
        return stockMapper.findTradeByStockCode(pageRequest, memberId, stockCode);
    }

    public int getSearchTotalCount(String keyword) {
        return stockMapper.findSearchTotalCount(keyword);
    }

    public int getHistoryTotalCount(Long memberId, String stockCode) {
        return stockMapper.findHistoryTotalCount(memberId, stockCode);
    }

    public List<UserStock> getAllUserStocks(Long memberId) {
        return stockMapper.findAllUserStocks(memberId);
    }
}