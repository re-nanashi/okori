package com.okori.workout_tracker_api.repository;

import com.okori.workout_tracker_api.entity.WorkoutSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutScheduleRepository extends JpaRepository<WorkoutSchedule, Long> {
    List<WorkoutSchedule> findByWorkoutId(Long id);
}
