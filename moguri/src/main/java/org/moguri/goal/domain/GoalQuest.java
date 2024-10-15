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
    private int categoryId;
    private String categoryName; // 카테고리 이름 추가
    private BigDecimal targetPercent;
    private BigDecimal currentAmount;
    private int questDays;
    private BigDecimal rewardAmount;
}
