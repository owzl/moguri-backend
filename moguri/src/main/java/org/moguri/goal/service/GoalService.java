package org.moguri.goal.service;

import lombok.RequiredArgsConstructor;
import org.moguri.accountbook.domain.AccountBook;
import org.moguri.accountbook.repository.AccountBookMapper;
import org.moguri.accountbook.service.AccountBookService;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.PageRequest;
import org.moguri.exception.MoguriLogicException;
import org.moguri.goal.domain.Goal;
import org.moguri.goal.param.GoalCreateParam;
import org.moguri.goal.param.GoalUpdateParam;
import org.moguri.goal.repository.GoalMapper;

import org.moguri.goal.repository.GoalQuestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalService {

    final private GoalMapper goalMapper;
    private final AccountBookMapper accountBookMapper;
    private final GoalQuestMapper goalQuestMapper;

    public Goal getGoal(Long goalId) {
        Goal goal = goalMapper.getGoal(goalId);
        return Optional.ofNullable(goal)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
    }

    public List<Goal> getList(PageRequest pageRequest) {
        return goalMapper.findAll(pageRequest);

    }

    public int getTotalCount() {
        return goalMapper.getTotalCount();
    }

    public void update(GoalUpdateParam param) {
        Optional.ofNullable(param.getGoalId())
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));

        Goal goal = goalMapper.getGoal(param.getGoalId());

        // GoalUpdateParam을 업데이트할 필드로 설정
        GoalUpdateParam updateParam = GoalUpdateParam.builder()
                .goalId(goal.getGoalId())
                .memberId(param.getMemberId())
                .goalName(param.getGoalName())
                .currentAmount(param.getCurrentAmount())
                .targetPercent(param.getTargetPercent())
                .startDate(param.getStartDate())
                .endDate(param.getEndDate())
                .goalCategory(param.getGoalCategory())
                .rewardAmount(param.getRewardAmount())
                .build();

        // 저축 목표의 경우
        if (goal.getGoalCategory() == null) {
            // 사용자가 입력한 goalAmount를 설정
            updateParam.setGoalAmount(param.getGoalAmount());
        }
        // 지출 목표의 경우
        else {
            // 지출 목표의 goalAmount를 계산
            BigDecimal goalAmount = accountBookMapper.getGoalAmountForCategory(goal.getGoalCategory());
            updateParam.setGoalAmount(goalAmount);
        }

        // Goal 객체를 데이터베이스에 업데이트
        goalMapper.update(updateParam);
    }


    public void create(GoalCreateParam param) {
        Goal goal = param.toEntity();

        // 저축 목표의 경우
        if (goal.getGoalCategory() == null) {
            // 사용자가 입력한 goalAmount를 설정
            if (param.getGoalAmount() == null) {
                throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
            }
            goal.setGoalAmount(param.getGoalAmount());
        }
        // 지출 목표의 경우
        else {
            // 지출 목표의 goalAmount를 계산
            BigDecimal goalAmount = accountBookMapper.getGoalAmountForCategory(goal.getGoalCategory());
            if (goalAmount == null) {
                throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
            }
            goal.setGoalAmount(goalAmount);
        }

        // Goal을 데이터베이스에 저장
        goalMapper.create(goal);
    }


    public void delete(long goalId) {
        Optional.ofNullable(goalId)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
        goalMapper.delete(goalId);
    }


}
