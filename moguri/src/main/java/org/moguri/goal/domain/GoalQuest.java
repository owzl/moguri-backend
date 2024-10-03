package org.moguri.goal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalQuest {
    private long questId;
    private String questTitle;
    private String questDescription;
    private long categoryId;  // Category와 연결
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private int questDays;
    private BigDecimal rewardAmount;

}
