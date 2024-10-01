package org.moguri.goal.service;

import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.PageRequest;
import org.moguri.exception.MoguriLogicException;
import org.moguri.goal.domain.Goal;
import org.moguri.goal.param.GoalCreateParam;
import org.moguri.goal.param.GoalUpdateParam;
import org.moguri.goal.repository.GoalMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional(readOnly = true) //list조회 및 페이지네이션
    public List<Goal> getList(PageRequest pageRequest) {
        return goalMapper.findAll(pageRequest);
    }

    public int getTotalCount() {
        return goalMapper.getTotalCount();
    }

    public void update(GoalUpdateParam param) {
        Optional.ofNullable(param.getGoalId())
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
        goalMapper.update(param);

    }

    public void create(GoalCreateParam param) {
        Goal goal = param.toEntity();
        goalMapper.create(goal);
    }

    public void delete(long goalId) {
        Optional.ofNullable(goalId)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
        goalMapper.delete(goalId);
    }
}
