package org.moguri.goal.controller;

import lombok.RequiredArgsConstructor;
import org.moguri.common.response.ApiResponse;
import org.moguri.goal.dto.GoalDTO;
import org.moguri.goal.service.GoalService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goal")
@RequiredArgsConstructor
public class GoalController {
    private final GoalService service;

    @GetMapping("/{goalId}")
    public ApiResponse<GoalDTO> get(@PathVariable int goalId) {
        return ApiResponse.of(service.get(goalId));
    }

    @PostMapping("")
    public ApiResponse<GoalDTO> create(@RequestBody GoalDTO goal) {
        return ApiResponse.of(service.create(goal));
    }

    @PutMapping("/{goalId}")
    public ApiResponse<GoalDTO> update(@PathVariable int goalId, @RequestBody GoalDTO goal) {
        goal.setGoalId(goalId);
        return ApiResponse.of(service.update(goal));
    }

    @DeleteMapping("/{goalId}")
    public ApiResponse<GoalDTO> delete(@PathVariable int goalId) {
        return ApiResponse.of(service.delete(goalId));
    }
}
