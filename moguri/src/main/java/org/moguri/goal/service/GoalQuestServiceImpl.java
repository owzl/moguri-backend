package org.moguri.goal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moguri.common.response.PageRequest;
import org.moguri.goal.domain.GoalQuest;
import org.moguri.goal.repository.GoalQuestMapper;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoalQuestServiceImpl implements GoalQuestService{

    private final GoalQuestMapper goalQuestMapper;

    // 목표 퀘스트 리스트 조회
    @Override
    public List<GoalQuest> getGoalQuests(PageRequest pageRequest) {
        return goalQuestMapper.getGoalQuests(pageRequest);
    }

    // 목표 퀘스트 개수 - 페이징
    @Override
    public int getTotalGoalQuestsCount() {
        return goalQuestMapper.getTotalGoalQuestsCount();
    }

}
