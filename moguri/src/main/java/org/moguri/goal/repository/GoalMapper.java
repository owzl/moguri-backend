package org.moguri.goal.repository;

import org.moguri.goal.domain.Goal;
import org.moguri.goal.param.GoalUpdateParam;

import java.util.List;

public interface GoalMapper {
    Goal getGoal(long goalId);

    void create(Goal goal);

    void update(GoalUpdateParam param);

    long delete(long goalId);

    List<Goal> getList();
}
