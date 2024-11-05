package org.moguri.stock.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.moguri.stock.enums.TradeType;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class TradeHistory {
    private String stockNameKR;
    private String marketType;
    private int price;
    private int quantity;
    private int totalAmount;
    private TradeType tradeType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date tradeAt;
}