package com.okori.workout_tracker_api.service.workout;

import com.okori.workout_tracker_api.dto.WorkoutDTO;
import com.okori.workout_tracker_api.entity.Workout;
import com.okori.workout_tracker_api.exceptions.ResourceNotFoundException;
import com.okori.workout_tracker_api.request.AddWorkoutRequest;
import com.okori.workout_tracker_api.request.WorkoutUpdateRequest;

import java.util.List;

public interface IWorkoutService {
    Workout addWorkout(AddWorkoutRequest request);
    Workout getWorkoutById(Long id) throws ResourceNotFoundException;
    Workout updateWorkout(WorkoutUpdateRequest request, Long workoutId) throws ResourceNotFoundException;
    void deleteWorkoutById(Long id) throws ResourceNotFoundException;
    List<Workout> getAllWorkouts();
    List<Workout> getWorkoutsByName(String name); //
    List<Workout> getWorkoutsByCategory(String category); //
    List<Workout> getWorkoutsByNameAndCategory(String name, String category);
    int countWorkoutsByCategory(String category);
    List<WorkoutDTO> getConvertedWorkouts(List<Workout> workouts);
    WorkoutDTO convertToDto(Workout workout);
    // TODO:
    //  [1] Get workouts by UserId
    //  [2] Get workouts By UserId and Category
    //  [3] Count workouts By User
}