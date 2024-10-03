package org.moguri.goal.repository;

import org.moguri.common.response.PageRequest;
import org.moguri.goal.domain.GoalQuest;

import java.util.List;

public interface GoalQuestMapper {

    // 목표 퀘스트 리스트 조회
    List<GoalQuest> getGoalQuests(PageRequest pageRequest);

    // 목표 퀘스트 개수 - 페이징
    int getTotalGoalQuestsCount();
}
