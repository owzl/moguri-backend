package org.moguri.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.ApiResponse;
import org.moguri.common.response.MoguriPage;
import org.moguri.common.response.PageRequest;
import org.moguri.common.validator.PageLimitSizeValidator;
import org.moguri.member.service.MemberService;
import org.moguri.stock.domain.Stock;
import org.moguri.stock.enums.Period;
import org.moguri.stock.enums.TradeType;
import org.moguri.stock.param.StockBuyParam;
import org.moguri.stock.param.StockSellParam;
import org.moguri.stock.service.StockService;
import org.moguri.stock.stockResponse.Output;
import org.moguri.stock.stockResponse.StockChart;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    private final MemberService memberService;

    @GetMapping("/price/{stockCode}")
    public ApiResponse<?> getPrice(@PathVariable("stockCode") String stockCode) throws JsonProcessingException {
        Output output = stockService.getPresentPrice(stockCode);
        return ApiResponse.of(PriceItem.of(output));
    }

    @GetMapping("/chart/{stockCode}")
    public ApiResponse<?> getStockChart(@PathVariable("stockCode") String stockCode, @RequestParam(defaultValue = "DAY") Period period) throws JsonProcessingException {
        List<StockChart> stockChart = stockService.getStockChart(stockCode, period);
        return ApiResponse.of(stockChart);
    }

    @GetMapping
    public ApiResponse<?> getStockByKeyword(StockGetRequest request, @RequestParam String keyword) {
        PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit());

        List<Stock> stocks = stockService.findStockByKeyword(pageRequest, keyword);
        int totalCount = stockService.getTotalCount();
        return ApiResponse.of(MoguriPage.of(pageRequest, totalCount,
                stocks.stream().map(StockController.StockItem::of).toList()));
    }

    @PostMapping("/price/{stockCode}")
    public ApiResponse<?> buyStock(@PathVariable String stockCode, @RequestBody StockBuyRequest request) {
        StockBuyParam param = request.convert(stockCode);
        stockService.buyStock(param);

        //memberService.updateCottonCandy()
        // 코튼 캔디 빼서 사기
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @PatchMapping("/price/{stockCode}")
    public ApiResponse<?> sellStock(@PathVariable String stockCode, @RequestBody StockSellRequest request) {
        StockSellParam param = request.convert(stockCode);
        stockService.sellStock(param);

        //memberService.updateCottonCandy()
        //코튼 캔디 판 만큼 추가
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @Data
    private static class PriceItem {

        private String prdy_ctrt; // id

        private String stck_prpr;

        private static PriceItem of(Output output) {
            PriceItem converted = new PriceItem();
            converted.prdy_ctrt = output.getPrdy_ctrt();
            converted.stck_prpr = output.getStck_prpr();
            return converted;
        }
    }

    @Data
    private static class StockItem {

        private String stockCode; // id

        private String stockNameKR;

        private String stockNameEN;

        private static StockItem of(Stock stock) {
            StockItem converted = new StockItem();
            converted.stockCode = stock.getStockCode();
            converted.stockNameKR = stock.getStockNameKR();
            converted.stockNameEN = stock.getStockNameEN();
            return converted;
        }
    }

    @Data
    private static class StockGetRequest {

        private int page = 0;
        private int limit = 30;
        //default 값
    }

    @Data
    private static class StockBuyRequest {

        private long memberId; // id

        private int price;

        private int quantity;

        public StockBuyParam convert(String stockCode) {
            StockBuyParam param = StockBuyParam.builder()
                    .stockCode(stockCode)
                    .memberId(memberId)
                    .price(price)
                    .quantity(quantity)
                    .tradeType(TradeType.BUY)
                    .build();
            return param;
        }
    }

    @Data
    private static class StockSellRequest {

        private long memberId; // id

        private int price;

        private int quantity;

        public StockSellParam convert(String stockCode) {
            StockSellParam param = StockSellParam.builder()
                    .stockCode(stockCode)
                    .memberId(memberId)
                    .price(price)
                    .quantity(quantity)
                    .tradeType(TradeType.SELL)
                    .build();
            return param;
        }
    }
}

/**
 * 매수매도, 차트 200개 더 가져오기, redis 캐싱, 거래내역(최근 50건, 체결 내역), 검색구현(비슷한거 다 긁어오는거)
 */