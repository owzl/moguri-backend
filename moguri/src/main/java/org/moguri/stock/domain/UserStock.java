package org.moguri.stock.domain;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class UserStock {
    private String stockCode;
    private String nameKr;
    private String marketType;
    private int quantity;
    private int averagePrice;
}
