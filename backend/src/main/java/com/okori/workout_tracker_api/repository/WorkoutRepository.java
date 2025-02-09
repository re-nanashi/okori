package com.okori.workout_tracker_api.repository;

import com.okori.workout_tracker_api.entity.User;
import com.okori.workout_tracker_api.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Optional<Workout> findByUserAndId(User user, Long workoutId);
    List<Workout> findAllByUser(User user);
}
