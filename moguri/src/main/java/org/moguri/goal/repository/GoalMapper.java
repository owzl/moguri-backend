package org.moguri.goal.repository;

import org.moguri.goal.domain.Goal;

public interface GoalMapper {
    public Goal getGoal(long goalId);

    public void create(Goal goal);

    public long update(Goal goal);

    public long delete(long goalId);

}
