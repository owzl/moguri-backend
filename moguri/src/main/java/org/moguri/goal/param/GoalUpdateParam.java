package org.moguri.goal.param;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class GoalUpdateParam {
    private long goalId;
    private long memberId;
    private String goalName;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private BigDecimal targetPercent; // 추가
    private Date startDate;
    private Date endDate;
    private String goalCategory;
    private BigDecimal rewardAmount;
    private long questId; // 추가
}
