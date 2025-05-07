package com.example.demo.service;

import com.example.demo.entity.Schedule;
import com.example.demo.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> getAllSchedules(Long userId) {
        return scheduleRepository.findByUserId(userId);
    }

    public Schedule getScheduleById(Long id, Long userId) {
        return scheduleRepository.findById(id)
                .filter(schedule -> schedule.getUserId().equals(userId))
                .orElse(null);
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Long id, Schedule schedule, Long userId) {
        return scheduleRepository.findById(id)
                .filter(existingSchedule -> existingSchedule.getUserId().equals(userId))
                .map(existingSchedule -> {
                    existingSchedule.setTitle(schedule.getTitle());
                    existingSchedule.setContent(schedule.getContent());
                    existingSchedule.setStartTime(schedule.getStartTime());
                    existingSchedule.setEndTime(schedule.getEndTime());
                    existingSchedule.setPriority(schedule.getPriority());
                    existingSchedule.setTags(schedule.getTags());
                    existingSchedule.setGroupId(schedule.getGroupId());
                    return scheduleRepository.save(existingSchedule);
                })
                .orElse(null);
    }

    public boolean deleteSchedule(Long id, Long userId) {
        return scheduleRepository.findById(id)
                .filter(schedule -> schedule.getUserId().equals(userId))
                .map(schedule -> {
                    scheduleRepository.delete(schedule);
                    return true;
                })
                .orElse(false);
    }

    public List<Schedule> getSchedulesByPriority(Long userId, String priority) {
        return scheduleRepository.findByUserIdAndPriority(userId, priority);
    }

    public List<Schedule> getSchedulesByTag(Long userId, String tag) {
        return scheduleRepository.findByUserIdAndTagsContaining(userId, tag);
    }

    public List<Schedule> getSchedulesByGroup(Long userId, Long groupId) {
        return scheduleRepository.findByUserIdAndGroupId(userId, groupId);
    }

    //打卡
    public Schedule punchSchedule(Long id, Long userId) {
        return scheduleRepository.findById(id)
                .filter(schedule -> schedule.getUserId().equals(userId))
                .map(schedule -> {
                    schedule.setIsFinished(!schedule.getIsFinished());
                    return scheduleRepository.save(schedule);
                })
                .orElseThrow(() -> new RuntimeException("日程不存在"));
    }
} 