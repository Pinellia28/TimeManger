package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.entity.Schedule;
import com.example.demo.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ApiResponse<List<Schedule>> getAllSchedules(@RequestHeader(value = "X-User-ID", required = false) Long userId) {
        if (userId == null) {
            return ApiResponse.fail(401, "Not logged in");
        }
        return ApiResponse.success(scheduleService.getAllSchedules(userId));
    }

    @GetMapping("/{id}")
    public ApiResponse<Schedule> getScheduleById(@PathVariable Long id, @RequestHeader(value = "X-User-ID", required = false) Long userId) {
        if (userId == null) {
            return ApiResponse.fail(401, "Not logged in");
        }
        Schedule schedule = scheduleService.getScheduleById(id, userId);
        if (schedule == null) {
            return ApiResponse.fail(404, "Schedule not found");
        }
        return ApiResponse.success(schedule);
    }

    @PostMapping
    public ApiResponse<Schedule> createSchedule(@RequestBody Schedule schedule, @RequestHeader(value = "X-User-ID", required = false) Long userId) {
        if (userId == null) {
            return ApiResponse.fail(401, "Not logged in");
        }
        schedule.setUserId(userId);
        return ApiResponse.success(scheduleService.createSchedule(schedule));
    }

    //打卡
    @PostMapping("/{id}/punch")
    public ApiResponse<Schedule> punchSchedule(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-ID", required = false) Long userId) {
        if (userId == null) {
            return ApiResponse.fail(401, "Not logged in");
        }
        Schedule punchedSchedule = scheduleService.punchSchedule(id, userId);
        if (punchedSchedule == null) {
            return ApiResponse.fail(404, "Schedule not found");
        }
        return ApiResponse.success(punchedSchedule);
    }

    @PutMapping("/{id}")
    public ApiResponse<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule, @RequestHeader(value = "X-User-ID", required = false) Long userId) {
        if (userId == null) {
            return ApiResponse.fail(401, "Not logged in");
        }
        Schedule updatedSchedule = scheduleService.updateSchedule(id, schedule, userId);
        if (updatedSchedule == null) {
            return ApiResponse.fail(404, "Schedule not found");
        }
        return ApiResponse.success(updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSchedule(@PathVariable Long id, @RequestHeader(value = "X-User-ID", required = false) Long userId) {
        if (userId == null) {
            return ApiResponse.fail(401, "Not logged in");
        }
        if (scheduleService.deleteSchedule(id, userId)) {
            return ApiResponse.success(null);
        }
        return ApiResponse.fail(404, "Schedule not found");
    }

    @GetMapping("/priority/{priority}")
    public ApiResponse<List<Schedule>> getSchedulesByPriority(@PathVariable String priority, @RequestHeader(value = "X-User-ID", required = false) Long userId) {
        if (userId == null) {
            return ApiResponse.fail(401, "Not logged in");
        }
        return ApiResponse.success(scheduleService.getSchedulesByPriority(userId, priority));
    }

    @GetMapping("/tag/{tag}")
    public ApiResponse<List<Schedule>> getSchedulesByTag(@PathVariable String tag, @RequestHeader(value = "X-User-ID", required = false) Long userId) {
        if (userId == null) {
            return ApiResponse.fail(401, "Not logged in");
        }
        return ApiResponse.success(scheduleService.getSchedulesByTag(userId, tag));
    }

    @GetMapping("/group/{groupId}")
    public ApiResponse<List<Schedule>> getSchedulesByGroup(@PathVariable Long groupId, @RequestHeader(value = "X-User-ID", required = false) Long userId) {
        if (userId == null) {
            return ApiResponse.fail(401, "Not logged in");
        }
        return ApiResponse.success(scheduleService.getSchedulesByGroup(userId, groupId));
    }
} 