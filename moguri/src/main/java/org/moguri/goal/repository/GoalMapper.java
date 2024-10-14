package org.moguri.goal.repository;

import org.apache.ibatis.annotations.Param;
import org.moguri.common.response.PageRequest;
import org.moguri.goal.domain.Goal;
import org.moguri.goal.param.GoalUpdateParam;

import java.util.List;

public interface GoalMapper {
    List<Goal> findAll(@Param("pageRequest") PageRequest pageRequest, @Param("memberId") long memberId);

    int getTotalCount(@Param("memberId") long memberId);

    Goal getGoal(long goalId);

    void create(Goal goal);

    void update(GoalUpdateParam param);

    long delete(long goalId);


    /* === 목표와 연동 === */
    // currentAmount 업데이트
    void updateCurrentAmount(GoalUpdateParam updateParam);

    // 지출 목표 관련(카테고리)
    Goal findByGoalCategory(String category);

    // 저축 목표 관련(상세설명)
    Goal findByGoalName(String description);
}
