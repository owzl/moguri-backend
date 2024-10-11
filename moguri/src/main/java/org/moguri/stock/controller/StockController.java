package org.moguri.stock.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.ApiResponse;
import org.moguri.common.response.MoguriPage;
import org.moguri.common.response.PageRequest;
import org.moguri.common.validator.PageLimitSizeValidator;
import org.moguri.stock.domain.Stock;
import org.moguri.stock.domain.TradeHistory;
import org.moguri.stock.domain.UserStock;
import org.moguri.stock.enums.Period;
import org.moguri.stock.enums.TradeType;
import org.moguri.stock.param.StockTradeParam;
import org.moguri.stock.service.StockService;
import org.moguri.stock.stockResponse.Output;
import org.moguri.stock.stockResponse.StockChart;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

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
    public ApiResponse<?> getStockByKeyword(@RequestParam String keyword, StockGetRequest request) {
        PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit());

        List<Stock> stocks = stockService.findStockByKeyword(pageRequest, keyword);
        int totalCount = stockService.getSearchTotalCount(keyword);

        return ApiResponse.of(MoguriPage.of(pageRequest, totalCount,
                stocks.stream().map(StockController.StockItem::of).toList()));
    }

    @PostMapping("/price/{stockCode}/{tradeType}")
    public ApiResponse<?> tradeStock(@PathVariable("stockCode") String stockCode, @PathVariable("tradeType") TradeType tradeType, @RequestBody StockTradeRequest request) {
        StockTradeParam param = request.convert(stockCode, tradeType);
        stockService.tradeStock(param);

        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @GetMapping("/{stockCode}/{memberId}")
    public ApiResponse<?> getTradeHistory(@PathVariable("stockCode") String stockCode, @PathVariable("memberId") Long memberId, HistoryGetRequest request) {
        PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit());

        List<TradeHistory> tradeHistory = stockService.getTradeHistory(pageRequest, memberId, stockCode);
        int totalCount = stockService.getHistoryTotalCount(memberId, stockCode);

        return ApiResponse.of(MoguriPage.of(pageRequest, totalCount,
                tradeHistory.stream().map(HistoryItem::of).toList()));
    }

    @GetMapping("/{memberId}")
    public ApiResponse<?> getAllUserStocks(@PathVariable("memberId") Long memberId) {
        List<UserStock> userStocks = stockService.getAllUserStocks(memberId);
        return ApiResponse.of(userStocks);
    }

    @Data
    private static class UserStockItem {

        private String stockCode;

        private String nameKr;

        private String marketType;

        private int quantity;

        private int averagePrice;

        private static UserStockItem of(UserStock userStock) {
            UserStockItem converted = new UserStockItem();
            converted.stockCode = userStock.getStockCode();
            converted.nameKr = userStock.getNameKr();
            converted.marketType = userStock.getMarketType();
            converted.quantity = userStock.getQuantity();
            converted.averagePrice = userStock.getAveragePrice();
            return converted;
        }
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

        private String marketType;

        private static StockItem of(Stock stock) {
            StockItem converted = new StockItem();
            converted.stockCode = stock.getStockCode();
            converted.stockNameKR = stock.getNameKr();
            converted.stockNameEN = stock.getNameEn();
            converted.marketType = stock.getMarketType();
            return converted;
        }
    }

    @Data
    private static class HistoryItem {
        private String stockNameKR;

        private String marketType;

        private int price;

        private int quantity;

        private int totalAmount;

        private TradeType tradeType;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private Date tradeAt;

        private static HistoryItem of(TradeHistory history) {
            HistoryItem converted = new HistoryItem();
            converted.stockNameKR = history.getStockNameKR();
            converted.marketType = history.getMarketType();
            converted.price = history.getPrice();
            converted.quantity = history.getQuantity();
            converted.totalAmount = history.getTotalAmount();
            converted.tradeType = history.getTradeType();
            converted.tradeAt = history.getTradeAt();
            return converted;
        }
    }

    @Data
    private static class HistoryGetRequest {
        private int page = 0;
        private int limit = 10;
        //default 값
    }

    @Data
    private static class StockGetRequest {
        private int page = 0;
        private int limit = 20;
        //default 값
    }

    @Data
    private static class StockTradeRequest {

        private long memberId; // 고유 id

        private int price;

        private int quantity;

        private int totalAmount;

        public StockTradeParam convert(String stockCode, TradeType tradeType) {
            StockTradeParam param = StockTradeParam.builder()
                    .stockCode(stockCode)
                    .memberId(memberId)
                    .price(price)
                    .quantity(quantity)
                    .totalAmount(totalAmount)
                    .tradeType(tradeType)
                    .build();
            return param;
        }
    }
}