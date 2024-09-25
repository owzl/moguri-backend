package org.moguri.goal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.moguri.goal.domain.GoalVO;
import org.moguri.goal.dto.GoalDTO;
import org.moguri.goal.mapper.GoalMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j
@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {
    final private GoalMapper mapper;

    @Override
    public GoalDTO get(int goalId) {
        GoalVO goalVO = mapper.get(goalId);
        GoalDTO goal = GoalDTO.of(goalVO);
        return Optional.ofNullable(goal)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    @Override
    public GoalDTO create(GoalDTO goal) {
        GoalVO goalVO = goal.toVO();
        mapper.create(goalVO);

        return get(goalVO.getGoalId());
    }

    @Override
    public GoalDTO update(GoalDTO goal) {
        mapper.update(goal.toVO());
        return get(goal.getGoalId());
    }

    @Override
    public GoalDTO delete(int goalId) {
        GoalDTO goal = get(goalId);
        mapper.delete(goalId);
        return goal;
    }

}
