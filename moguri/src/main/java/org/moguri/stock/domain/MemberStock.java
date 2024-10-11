package org.moguri.stock.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class MemberStock {

    private long memberId;
    private long stockId;
    private int stockQuantity;
    private int buyPrice;
    private LocalDateTime buyAt;
    private int sellPrice;
    private LocalDateTime sellAt;
}