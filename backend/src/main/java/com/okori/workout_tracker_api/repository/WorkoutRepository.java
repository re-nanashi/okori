package com.okori.workout_tracker_api.repository;

import com.okori.workout_tracker_api.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findAllByCategory(String category);
    List<Workout> findAllByNameContainingIgnoreCase(String name);
    List<Workout> findAllByNameContainingIgnoreCaseAndCategory(String name, String category);
}
