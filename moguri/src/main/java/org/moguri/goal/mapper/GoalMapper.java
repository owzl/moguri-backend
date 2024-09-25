package org.moguri.goal.mapper;

import org.moguri.goal.domain.GoalVO;

public interface GoalMapper {
    public GoalVO get(int goalId);

    public void create(GoalVO goal);

    public int update(GoalVO goal);

    public int delete(int goalId);
}
