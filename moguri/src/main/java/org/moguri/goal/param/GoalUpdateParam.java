package org.moguri.goal.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GoalUpdateParam {

    private String goalName;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private Date startDate;
    private Date endDate;


}
