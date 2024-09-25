package org.moguri.goal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.moguri.goal.domain.GoalVO;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalDTO {
    private int goalId;
    private int memberId;
    private String goalName;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private Date createdAt;
    private Date updatedAt;
    private Date startDate;
    private Date endDate;

    //VO->DTO 변환
    public static GoalDTO of(GoalVO vo) {
        return vo == null ? null : GoalDTO.builder()
                .goalId(vo.getGoalId())
                .memberId(vo.getMemberId())
                .goalName(vo.getGoalName())
                .goalAmount(vo.getGoalAmount())
                .currentAmount(vo.getCurrentAmount())
                .startDate(vo.getStartDate())
                .endDate(vo.getEndDate())
                .createdAt(vo.getCreatedAt())
                .updatedAt(vo.getUpdatedAt())
                .build();

    }

    //DTO->VO 변환
    public GoalVO toVO() {
        return GoalVO.builder()
                .goalId(goalId)
                .memberId(memberId)
                .goalName(goalName)
                .goalAmount(goalAmount)
                .currentAmount(currentAmount)
                .startDate(startDate)
                .endDate(endDate)
                .updatedAt(updatedAt)
                .createdAt(createdAt)
                .build();
    }
}
