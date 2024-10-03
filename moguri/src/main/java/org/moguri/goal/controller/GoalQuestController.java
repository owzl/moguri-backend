package org.moguri.goal.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moguri.accountbook.controller.AccountBookController;
import org.moguri.accountbook.domain.AccountBook;
import org.moguri.common.response.ApiResponse;
import org.moguri.common.response.MoguriPage;
import org.moguri.common.response.PageRequest;
import org.moguri.common.validator.PageLimitSizeValidator;
import org.moguri.goal.domain.GoalQuest;
import org.moguri.goal.service.GoalQuestServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/goalquest")
@RequiredArgsConstructor
@Slf4j
public class GoalQuestController {

    private final GoalQuestServiceImpl goalQuestService;

    // 목표 퀘스트 리스트 조회
    @GetMapping("")
    public ApiResponse<List<GoalQuest>> getGoalQuests(GoalQuestGetRequest request) {
        PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit());

        List<GoalQuest> goalQuests = goalQuestService.getGoalQuests(pageRequest);
        int totalCount = goalQuestService.getTotalGoalQuestsCount();

        return ApiResponse.of(MoguriPage.of(pageRequest, totalCount,
                goalQuests.stream().map(GoalQuestController.GoalQuestItem::of).toList()));
    }

    /* 페이징 */
    @Data
    public static class GoalQuestGetRequest {
        private int page = 0; // 현재 페이지 번호
        private int limit = 30; // 페이지당 항목 수
    }

    /* 내부 DTO 클래스 */
    @Data
    public static class GoalQuestItem{
        private long questId;
        private String questTitle;
        private String questDescription;
        private long categoryId;  // Category와 연결
        private BigDecimal targetAmount;
        private BigDecimal currentAmount;
        private int questDays;
        private BigDecimal rewardAmount;

        public static GoalQuestItem of(GoalQuest goalQuest) {
            GoalQuestItem converted = new GoalQuestItem();
            converted.questId = goalQuest.getQuestId();
            converted.questTitle = goalQuest.getQuestTitle();
            converted.questDescription = goalQuest.getQuestDescription();
            converted.categoryId = goalQuest.getCategoryId();
            converted.targetAmount = goalQuest.getTargetAmount();
            converted.currentAmount = goalQuest.getCurrentAmount();
            converted.questDays = goalQuest.getQuestDays();
            converted.rewardAmount = goalQuest.getRewardAmount();

            return converted;
        }
    }

}
