package org.moguri.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.moguri.common.response.ApiResponse;
import org.moguri.stock.enums.Period;
import org.moguri.stock.service.StockService;
import org.moguri.stock.stockResponse.StockChart;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/price/{stockCode}")
    public ApiResponse<?> getPrice(@PathVariable("stockCode") String stockCode) {
        int presentPrice = stockService.getPresentPrice(stockCode);
        return ApiResponse.of(presentPrice);
    }

    @GetMapping("/chart/{stockCode}")
    public ApiResponse<?> getStockChart(@PathVariable("stockCode") String stockCode, @RequestParam(defaultValue = "DAY") Period period) throws JsonProcessingException {
        stockService.getToken();
        List<StockChart> stockChart = stockService.getStockChart(stockCode, period);
        return ApiResponse.of(stockChart);
    }

    @GetMapping
    public ApiResponse<?> getToken() {
        String token = stockService.getToken();
        return ApiResponse.of(token);
    }
//    @GetMapping
//    public ApiResponse<?> getStockName(@RequestParam String name) {
//        stockService.findStockByName(name);
//        return ApiResponse.of(ReturnCode.SUCCESS);
//    }

//    @PostMapping("/price/{stockCode}")
//    public ApiResponse<?> buyStock(@PathVariable String stockCode, @RequestBody StockBuyRequest request) {
//        StockParam param = request.convert(stockCode);
//        stockService.buyStock(param);
//
//        return ApiResponse.of(ReturnCode.SUCCESS);
//    }
//
//    @PatchMapping("/price/{stockCode}")
//    public ApiResponse<?> sellStock(@PathVariable String stockCode, @RequestBody StockSellRequest request) {
//        StockParam param = request.convert(stockCode);
//        stockService.sellStock(param);
//
//        memberService.update();
//        return ApiResponse.of(ReturnCode.SUCCESS);
//    }

//    @Data
//    private static class StockBuyRequest {
//
//        private long memberId; // id
//
//        private int price;
//
//        private int quantity;
//
//        public StockParam convert(String stockCode) {
//            StockParam param = StockParam.builder()
//                    .stockId(stockCode)
//                    .memberId(memberId)
//                    .price(price)
//                    .quantity(quantity)
//                    .tradeType(TradeType.BUY)
//                    .build();
//            return param;
//        }
//    }
//
//    @Data
//    private static class StockSellRequest {
//
//        private long memberId; // id
//
//        private int price;
//
//        private int quantity;
//
//        public StockParam convert(String stockCode) {
//            StockParam param = StockParam.builder()
//                    .stockId(stockCode)
//                    .memberId(memberId)
//                    .price(price)
//                    .quantity(quantity)
//                    .tradeType(TradeType.SELL)
//                    .build();
//            return param;
//        }
//    }
}