package org.moguri.goal.service;

import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.exception.MoguriLogicException;
import org.moguri.goal.domain.Goal;
import org.moguri.goal.param.GoalCreateParam;
import org.moguri.goal.param.GoalUpdateParam;
import org.moguri.goal.repository.GoalMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalService {

    final private GoalMapper goalMapper;

    public Goal getGoal(Long goalId) {
        Goal goal = goalMapper.getGoal(goalId);
        return Optional.ofNullable(goal)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
    }

    public Goal update(Long goalId, GoalUpdateParam param) throws MoguriLogicException {
        Goal goal = getGoal(goalId);
//    유효성검사
        if (param.getGoalName() == null) {
            throw new IllegalArgumentException("Goal name cannot be null");
        }
        goal.setGoalName(param.getGoalName());
        goal.setCurrentAmount(param.getCurrentAmount());
        goal.setStartDate(param.getStartDate());
        goal.setEndDate(param.getEndDate());
        goalMapper.update(goal);
        return getGoal(goal.getGoalId());
    }

    public void create(GoalCreateParam param) { //goal
        Goal goal = param.toEntity();
        goalMapper.create(goal);

    }

    public Goal delete(Long goalId) {
        Goal goal = getGoal(goalId);
        goalMapper.delete(goalId);
        return goal;
    }
}
