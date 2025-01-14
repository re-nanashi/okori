package com.okori.workout_tracker_api.repository;

import com.okori.workout_tracker_api.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findAllByNameContainingIgnoreCase(String name);
    List<Exercise> findAllByType(String type);
    List<Exercise> findAllByNameContainingIgnoreCaseAndType(String name, String type);
    List<Exercise> findByWorkoutId(Long id);
}
