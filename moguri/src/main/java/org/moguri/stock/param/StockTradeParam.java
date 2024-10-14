package org.moguri.stock.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.moguri.stock.domain.StockTrade;
import org.moguri.stock.enums.TradeType;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class StockTradeParam {
    private String stockCode;
    private long memberId;
    private int price;
    private int quantity;
    private int totalAmount;
    private TradeType tradeType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date tradeAt;

    public StockTrade toEntity() {
        return StockTrade.builder()
                .stockCode(stockCode)
                .memberId(memberId)
                .price(price)
                .quantity(quantity)
                .totalAmount(totalAmount)
                .tradeType(tradeType)
                .tradeAt(tradeAt)
                .build();
    }
}