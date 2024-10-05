package org.moguri.goal.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.ApiResponse;
import lombok.Data;
import org.moguri.common.response.MoguriPage;
import org.moguri.common.response.PageRequest;
import org.moguri.common.validator.PageLimitSizeValidator;
import org.moguri.goal.domain.Goal;
import org.moguri.goal.param.GoalCreateParam;
import org.moguri.goal.param.GoalUpdateParam;
import org.moguri.goal.service.GoalService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/goal")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @GetMapping("/{goalId}")
    public ApiResponse<?> getGoal(@PathVariable("goalId") long goalId) {
        Goal goal = goalService.getGoal(goalId);
        return ApiResponse.of(GoalItem.of(goal));
    }

    // 목표 목록 조회
    @GetMapping
    public ApiResponse<?> getList(GoalGetRequest request) {
        PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit());

        List<Goal> goals = goalService.getList(pageRequest);

        int totalCount = goalService.getTotalCount();
        return ApiResponse.of(MoguriPage.of(pageRequest, totalCount, goals.stream().map(GoalItem::of).toList()));
    }

    // 목표 추가
    @PostMapping
    public ApiResponse<?> create(@RequestBody GoalCreateRequest request) {
        GoalCreateParam param = request.convert();
        goalService.create(param);

        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @PatchMapping
    public ApiResponse<?> update(@RequestBody GoalUpdateRequest request) {
        GoalUpdateParam param = request.convert();
        System.out.print(param.toString());
        goalService.update(param);
        return ApiResponse.of(ReturnCode.SUCCESS);

    }

    @DeleteMapping("/{goalId}")
    public ApiResponse<?> delete(@PathVariable("goalId") Long goalId) {
        goalService.delete(goalId);
        return ApiResponse.of(ReturnCode.SUCCESS);
    }


    @Data //paginaiton
    private static class GoalGetRequest {
        private int page = 0;
        private int limit = 30;
    }

    @Data
    private static class GoalItem {
        private String goalName;
        private BigDecimal goalAmount;
        private BigDecimal currentAmount;
        private BigDecimal targetPercent;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private Date startDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private Date endDate;
        private String goalCategory;
        private BigDecimal rewardAmount;

        private static GoalItem of(Goal goal) {
            GoalItem converted = new GoalItem();
            converted.goalName = goal.getGoalName();
            converted.goalAmount = goal.getGoalAmount();
            converted.currentAmount = goal.getCurrentAmount();
            converted.targetPercent = goal.getTargetPercent();
            converted.startDate = goal.getStartDate();
            converted.endDate = goal.getEndDate();
            converted.goalCategory = goal.getGoalCategory();
            converted.rewardAmount = goal.getRewardAmount();
            return converted;
        }
    }

    @Data
    private static class GoalCreateRequest {
        private long memberId;
        private String goalName;
        private BigDecimal goalAmount;
        private BigDecimal currentAmount;
        private BigDecimal targetPercent;
        private Date startDate;
        private Date endDate;
        private String goalCategory;
        private BigDecimal rewardAmount;

        public GoalCreateParam convert() {
            GoalCreateParam param = GoalCreateParam.builder()
                    .memberId(memberId)
                    .goalName(goalName)
                    .goalAmount(goalAmount)
                    .currentAmount(currentAmount)
                    .targetPercent(targetPercent)
                    .startDate(startDate)
                    .endDate(endDate)
                    .goalCategory(goalCategory)
                    .rewardAmount(rewardAmount)
                    .build();
            return param;
        }
    }

    @Data
    private static class GoalUpdateRequest {
        private long goalId;
        private long memberId;
        private String goalName;
        private BigDecimal goalAmount;
        private BigDecimal currentAmount;
        private BigDecimal targetPercent;
        private Date startDate;
        private Date endDate;
        private String goalCategory;
        private BigDecimal rewardAmount;

        public GoalUpdateParam convert() {
            GoalUpdateParam param = GoalUpdateParam.builder()
                    .goalId(goalId)
                    .memberId(memberId)
                    .goalName(goalName)
                    .goalAmount(goalAmount)
                    .currentAmount(currentAmount)
                    .targetPercent(targetPercent)
                    .startDate(startDate)
                    .endDate(endDate)
                    .goalCategory(goalCategory)
                    .rewardAmount(rewardAmount)
                    .build();
            return param;
        }
    }
}
