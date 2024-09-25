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
    private String goalName;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private Date startDate;
    private Date endDate;

    public Goal toEntity(){
        Goal goal = Goal.builder()
                .goalName(goalName)
                .goalAmount(goalAmount)
                .currentAmount(currentAmount)
                .startDate(startDate)
                .endDate(endDate).build();
        return goal;
    }
}
