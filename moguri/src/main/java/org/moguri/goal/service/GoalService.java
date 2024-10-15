package org.moguri.goal.service;

import lombok.RequiredArgsConstructor;
import org.moguri.accountbook.repository.AccountBookMapper;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.PageRequest;
import org.moguri.exception.MoguriLogicException;
import org.moguri.goal.domain.Goal;
import org.moguri.goal.domain.GoalQuest;
import org.moguri.goal.param.GoalCreateParam;
import org.moguri.goal.param.GoalUpdateParam;
import org.moguri.goal.repository.GoalMapper;

import org.moguri.goal.repository.GoalQuestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalService {

    final private GoalMapper goalMapper;
    private final AccountBookMapper accountBookMapper;
    private final GoalQuestMapper goalQuestMapper;

    /* 개별 목표 조회 */
    public Goal getGoal(Long goalId) {
        Goal goal = goalMapper.getGoal(goalId);
        return Optional.ofNullable(goal)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
    }

    /* 목표 리스트 조회 */
    public List<Goal> getList(PageRequest pageRequest, long memberId) {
        return goalMapper.findAll(pageRequest, memberId);
    }

    /* 목표 총 개수 */
    public int getTotalCount(long memberId) {
        return goalMapper.getTotalCount(memberId);
    }

    /* 목표 수정 */
    public void update(GoalUpdateParam param) {
        Optional.ofNullable(param.getGoalId())
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));

        Goal goal = goalMapper.getGoal(param.getGoalId());

        // GoalUpdateParam을 업데이트할 필드로 설정
        GoalUpdateParam updateParam = GoalUpdateParam.builder()
                .goalId(goal.getGoalId())
                .memberId(param.getMemberId())
                .goalName(param.getGoalName())
                .currentAmount(param.getCurrentAmount())
                .targetPercent(param.getTargetPercent())
                .startDate(param.getStartDate())
                .endDate(param.getEndDate())
                .goalCategory(param.getGoalCategory())
                .rewardAmount(param.getRewardAmount())
                .questId(param.getQuestId())
                .build();

        // questId를 업데이트할 Param에 추가
        updateParam.setQuestId(param.getQuestId());

        // 저축 목표의 경우
        if (goal.getGoalCategory() == null) {
            // 사용자가 입력한 goalAmount를 설정
            updateParam.setGoalAmount(param.getGoalAmount());
        } else {
            // 지출 목표의 goalAmount를 계산
            BigDecimal goalAmount = accountBookMapper.getGoalAmountForCategory(param.getQuestId());
            updateParam.setGoalAmount(goalAmount);
        }

        // Goal 객체를 데이터베이스에 업데이트
        goalMapper.update(updateParam);
    }

    /* 목표 생성 */
    public void create(GoalCreateParam param) {
        Goal goal = param.toEntity();

        // questId를 Goal 객체에 설정
        goal.setQuestId(param.getQuestId());

        System.out.println("골 카테고리 받아온거: " + goal.getGoalCategory());
        System.out.println("퀘스트 아이디 받아온거: " + goal.getQuestId());


        // 저축 목표의 경우
        if (goal.getGoalCategory() == null) {
            if (param.getGoalAmount() == null) {
                throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
            }
            // 사용자가 입력한 goalAmount를 설정
            goal.setGoalAmount(param.getGoalAmount());
        } else {
            // 지출 목표의 경우
            // 지출 목표의 goalAmount를 계산
            BigDecimal goalAmount = accountBookMapper.getGoalAmountForCategory(param.getQuestId());

            if (goalAmount == null) {
                throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
            }
            goal.setGoalAmount(goalAmount);
        }

        System.out.println("골 완전체 : " + goal);
        // Goal을 데이터베이스에 저장
        goalMapper.create(goal);
    }


    /* 목표 삭제 */
    public void delete(long goalId) {
        Optional.ofNullable(goalId)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
        goalMapper.delete(goalId);
    }


}
