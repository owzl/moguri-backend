package org.moguri.goal.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.moguri.goal.domain.Goal;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GoalCreateParam {

    private long memberId;
    private String goalName;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private BigDecimal targetPercent;
    private Date startDate;
    private Date endDate;
    private String goalCategory;
    private BigDecimal rewardAmount;
    private long questId; // 추가

    public Goal toEntity() {
        Goal goal = Goal.builder()
                .memberId(memberId)
                .goalName(goalName)
                .goalAmount(goalAmount)
                .currentAmount(currentAmount)
                .targetPercent(targetPercent)
                .startDate(startDate)
                .endDate(endDate)
                .goalCategory(goalCategory)
                .rewardAmount(rewardAmount)
                .questId(questId)
                .build();
        return goal;
    }
}
