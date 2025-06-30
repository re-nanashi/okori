package com.okori.workout_tracker_api.repository;

import com.okori.workout_tracker_api.entity.WorkoutSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutScheduleRepository extends JpaRepository<WorkoutSchedule, Long> {
    List<WorkoutSchedule> findByWorkoutId(Long id);
}
