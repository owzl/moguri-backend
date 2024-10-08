package org.moguri.stock.domain;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Stock {
    private String stockCode;
    private String stockNameKR;
    private String stockNameEN;
}
