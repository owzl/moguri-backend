package org.moguri.goal.service;

import org.moguri.goal.dto.GoalDTO;


public interface GoalService {

    public GoalDTO get(int goalId);

    public GoalDTO create(GoalDTO goal);

    public GoalDTO update(GoalDTO goal);

    public GoalDTO delete(int goalId);
}
