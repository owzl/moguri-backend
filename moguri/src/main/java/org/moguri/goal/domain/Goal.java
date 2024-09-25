package org.moguri.goal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goal {
    private long goalId;
    private int memberId;
    private String goalName;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private Date createdAt;
    private Date updatedAt;
    private Date startDate;
    private Date endDate;
}
