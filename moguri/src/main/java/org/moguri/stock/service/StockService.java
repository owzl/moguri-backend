
package org.moguri.stock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.exception.MoguriRequestException;
import org.moguri.stock.enums.ApiEndPoint;
import org.moguri.stock.enums.Period;
import org.moguri.stock.stockResponse.PriceResponse;
import org.moguri.stock.stockResponse.StockChart;
import org.moguri.stock.stockResponse.StockToken;
import org.moguri.stock.param.TokenParam;
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

@Service
@Transactional
@RequiredArgsConstructor
public class StockService {

    //private final StockMapper stockMapper;

    private final RestTemplate restTemplate;

    private String divCode;

    private final String appKey = "PSswIUPzvSE1UjogWsNLDkw94BPS0MF2QLI4";

    private final String appSecret = "LTVAkNmnPSKd73yuOni8b+1AMlftlSkZr0fBB51j65cAfMNxRdGcaqJaAD37MbAoFzqQTgrwzB41pKamVBUQxbfYq5Me7xz3l5RyvG+ttcKJBE/6wHSqjzoqLJhWgiOinIXnY1YNF7HegxJVKrKWVjdd0pR7AQP3K9vAjSR5kzbfRz6rrD4=";

    private final String grantType = "client_credentials";

    private final String baseUrl = "https://openapivts.koreainvestment.com:29443";

    private final String mktCode = "J";

    public String getToken() {
        String url = baseUrl + ApiEndPoint.GET_TOKEN.getPath();
        System.out.println(grantType);

        TokenParam param = TokenParam.builder()
                .grant_type(grantType)
                .appkey(appKey)
                .appsecret(appSecret)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TokenParam> entity = new HttpEntity<>(param, headers);
        ResponseEntity<StockToken> response = restTemplate.postForEntity(url, entity, StockToken.class);
        return response.getBody().getAccess_token();
    }

    public int getPresentPrice(String stockCode) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + ApiEndPoint.GET_PRESENT_PRICE.getPath())
                .queryParam("FID_COND_MRKT_DIV_CODE", mktCode)
                .queryParam("FID_INPUT_ISCD", stockCode)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6ImYyNzE1NjBlLTA5NGUtNDgyOS1hZTQ5LWJjYjMzMDViZWYyOSIsInByZHRfY2QiOiIiLCJpc3MiOiJ1bm9ndyIsImV4cCI6MTcyNzg4MzE4MiwiaWF0IjoxNzI3Nzk2NzgyLCJqdGkiOiJQU3N3SVVQenZTRTFVam9nV3NOTERrdzk0QlBTME1GMlFMSTQifQ.S0_cSbkudy16HFWqRE2eVBbs5VzGWBIVLlg4G9qwQXUz3X9XzW7IT-Fim6IsUJvGhPN8gneNBuPotQAG6mN9oA");
        headers.set("appkey", appKey);
        headers.set("appsecret", appSecret);
        headers.set("tr_id", "FHKST01010100");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<PriceResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, PriceResponse.class);

        return Integer.parseInt(response.getBody().getOutput().getStck_prpr());
    }

    public List<StockChart> getStockChart(String stockCode, Period period) throws JsonProcessingException {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // Period에 따라 startDate 계산
        LocalDate startDate;
        switch (period) {
            case DAY:
                divCode = "D";
                startDate = now.minusDays(100); // 100일 전
                break;
            case WEEK:
                divCode = "W";
                startDate = now.minusWeeks(100); // 100주 전의 첫 번째 영업일
                break;
            case MONTH:
                divCode = "M";
                startDate = now.minusMonths(100); // 100개월 전의 첫 번째 일자
                break;
            case YEAR:
                divCode = "Y";
                startDate = now.minusYears(100); // 100년 전의 첫 번째 일자
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

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/json");
        headers.set("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6ImYyNzE1NjBlLTA5NGUtNDgyOS1hZTQ5LWJjYjMzMDViZWYyOSIsInByZHRfY2QiOiIiLCJpc3MiOiJ1bm9ndyIsImV4cCI6MTcyNzg4MzE4MiwiaWF0IjoxNzI3Nzk2NzgyLCJqdGkiOiJQU3N3SVVQenZTRTFVam9nV3NOTERrdzk0QlBTME1GMlFMSTQifQ.S0_cSbkudy16HFWqRE2eVBbs5VzGWBIVLlg4G9qwQXUz3X9XzW7IT-Fim6IsUJvGhPN8gneNBuPotQAG6mN9oA");
        headers.set("appkey", appKey);
        headers.set("appsecret", appSecret);
        headers.set("tr_id", "FHKST03010100");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);

        // output2 리스트 추출
        List<Map<String, Object>> output2 = (List<Map<String, Object>>) responseMap.get("output2");

        // 필요한 데이터를 담을 List 생성
        List<StockChart> chartStockDataList = new ArrayList<>();
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
        return chartStockDataList;
    }
//    public void findStockByName(String name) {
//        Optional.ofNullable(stockMapper.findStockByName).orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
//        stockMapper.findByName;
//    }
//
//    public void buyStock(StockParam param) {
//        stockMapper.saveTrade(param);
//    }
//
//    public void sellStock(StockParam param) {
//        stockMapper.saveTrade(param);
//    }
}