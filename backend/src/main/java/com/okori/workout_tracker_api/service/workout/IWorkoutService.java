package com.okori.workout_tracker_api.service.workout;

import com.okori.workout_tracker_api.dto.WorkoutDTO;
import com.okori.workout_tracker_api.entity.User;
import com.okori.workout_tracker_api.entity.Workout;
import com.okori.workout_tracker_api.exceptions.ResourceNotFoundException;
import com.okori.workout_tracker_api.request.AddWorkoutRequest;
import com.okori.workout_tracker_api.request.WorkoutUpdateRequest;

import java.util.List;

public interface IWorkoutService {
    Workout addWorkout(AddWorkoutRequest request, User user);
    Workout getWorkoutById(Long id, User user) throws ResourceNotFoundException;
    List<Workout> getAllWorkoutsForUser(User user);
    Workout updateWorkout(WorkoutUpdateRequest request, Long workoutId, User user) throws ResourceNotFoundException;
    void deleteWorkoutById(Long id, User user) throws ResourceNotFoundException;
    WorkoutDTO convertToDto(Workout workout);
    List<WorkoutDTO> getConvertedWorkouts(List<Workout> workouts);
}