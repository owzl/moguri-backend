package org.moguri.stock.domain;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class InvestorRanking {
    private String nickName;
    private double profitPercentage;
}
