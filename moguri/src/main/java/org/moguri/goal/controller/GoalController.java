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

    @PostMapping("") //목표 설정하기
    public ApiResponse<?> create(@RequestBody GoalCreateRequest request) {
        GoalCreateParam param = request.convert();
        goalService.create(param);
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @PutMapping("/{goalId}")
    public ApiResponse<?> update(@PathVariable("goalId") Long goalId, @RequestBody GoalUpdateRequest request){
        GoalUpdateParam param = request.convert();
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @DeleteMapping("/{goalId}")
    public ApiResponse<?> delete(@PathVariable("goalId") Long goalId){
        Goal goal = goalService.getGoal(goalId);
        return ApiResponse.of(GoalItem.of(goal));
    }


    @Data
    private static class GoalItem {
        private String goalName;
        private BigDecimal goalAmount;
        private BigDecimal currentAmount;
        private Date startDate;
        private Date endDate;

        private static GoalItem of(Goal goal) {
            GoalItem converted = new GoalItem();
            converted.goalName = goal.getGoalName();
            converted.currentAmount = goal.getCurrentAmount();
            converted.startDate = goal.getStartDate();
            converted.endDate = goal.getEndDate();
            return converted;
        }
    }

    @Data
    private static class GoalCreateRequest {
        private String goalName;
        private BigDecimal goalAmount;
        private BigDecimal currentAmount;
        private Date startDate;
        private Date endDate;

        public GoalCreateParam convert() {
            GoalCreateParam param = GoalCreateParam.builder()
                    .goalName(goalName)
                    .currentAmount(currentAmount)
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();
            return param;
        }
    }

    @Data
    private static class GoalUpdateRequest {
        private String goalName;
        private BigDecimal goalAmount;
        private BigDecimal currentAmount;
        private Date startDate;
        private Date endDate;

        public GoalUpdateParam convert() {
            GoalUpdateParam param = GoalUpdateParam.builder()
                    .goalName(goalName)
                    .currentAmount(currentAmount)
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();
            return param;
        }
    }
}