package org.moguri.goal.service;

import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.exception.MoguriLogicException;
import org.moguri.goal.domain.Goal;
import org.moguri.goal.param.GoalCreateParam;
import org.moguri.goal.repository.GoalMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalService{

    final private GoalMapper goalMapper;

    public Goal getGoal(Long goalId) {
        Goal goal = goalMapper.getGoal(goalId);
        return Optional.ofNullable(goal)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
    }

    public Goal update(Goal goal){
        goalMapper.update(goal);
        return getGoal(goal.getGoalId());
    }
    //gpt
    public Goal create(GoalCreateParam param){
        Goal goal = param.toEntity();
        goalMapper.create(goal);
        return getGoal(goal.getGoalId());
    }

    public Goal delete(Long goalId){
        Goal goal = getGoal(goalId);
        goalMapper.delete(goalId);
        return goal;
    }
}

