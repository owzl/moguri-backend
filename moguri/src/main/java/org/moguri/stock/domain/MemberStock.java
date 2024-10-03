package org.moguri.stock.domain;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class MemberStock {

    private long memberId;
    private long stockId;
    private int stockQuantity;
    private int buyPrice;

}