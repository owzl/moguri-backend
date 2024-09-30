package org.moguri.goal.controller;

import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.ApiResponse;
import lombok.Data;
import org.moguri.goal.domain.Goal;
import org.moguri.goal.param.GoalCreateParam;
import org.moguri.goal.param.GoalUpdateParam;
import org.moguri.goal.service.GoalService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

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

    @GetMapping
    public ApiResponse<?> getList() {
        return ApiResponse.of(goalService.getList());
    }

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

    @Data
    private static class GoalItem {
        private String goalName;
        private BigDecimal goalAmount;
        private BigDecimal currentAmount;
        private Date startDate;
        private Date endDate;
        private String goalCategory;

        private static GoalItem of(Goal goal) {
            GoalItem converted = new GoalItem();
            converted.goalName = goal.getGoalName();
            converted.currentAmount = goal.getCurrentAmount();
            converted.startDate = goal.getStartDate();
            converted.endDate = goal.getEndDate();
            converted.goalCategory = goal.getGoalCategory();
            return converted;
        }
    }

    @Data
    private static class GoalCreateRequest {
        private long memberId;
        private String goalName;
        private BigDecimal goalAmount;
        private BigDecimal currentAmount;
        private Date startDate;
        private Date endDate;
        private String goalCategory;

        public GoalCreateParam convert() {
            GoalCreateParam param = GoalCreateParam.builder()
                    .memberId(memberId)
                    .goalName(goalName)
                    .goalAmount(goalAmount)
                    .currentAmount(currentAmount)
                    .startDate(startDate)
                    .endDate(endDate)
                    .goalCategory(goalCategory)
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
        private Date startDate;
        private Date endDate;
        private String goalCategory;

        public GoalUpdateParam convert() {
            GoalUpdateParam param = GoalUpdateParam.builder()
                    .goalId(goalId)
                    .memberId(memberId)
                    .goalName(goalName)
                    .goalAmount(goalAmount)
                    .currentAmount(currentAmount)
                    .startDate(startDate)
                    .endDate(endDate)
                    .goalCategory(goalCategory)
                    .build();
            return param;
        }
    }
}
