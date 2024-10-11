package org.moguri.stock.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.moguri.stock.enums.TradeType;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class StockBuyParam {
    private long memberId;
    private String stockCode;
    private TradeType tradeType;
    private int price;
    private int quantity;
    private LocalDateTime buyAt;
}
