package com.example.demo.repository;

import com.example.demo.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUserId(Long userId);
    List<Schedule> findByUserIdAndPriority(Long userId, String priority);
    List<Schedule> findByUserIdAndTagsContaining(Long userId, String tag);
    List<Schedule> findByUserIdAndGroupId(Long userId, Long groupId);

} 