package org.moguri.stock.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.moguri.stock.domain.StockTrade;
import org.moguri.stock.enums.TradeType;

import java.time.LocalDateTime;

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
    private LocalDateTime tradeAt;

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
