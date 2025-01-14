package com.okori.workout_tracker_api.service.workout;

import com.okori.workout_tracker_api.dto.WorkoutDTO;
import com.okori.workout_tracker_api.entity.Workout;
import com.okori.workout_tracker_api.exceptions.WorkoutNotFoundException;
import com.okori.workout_tracker_api.request.AddWorkoutRequest;

import java.util.List;

public interface IWorkoutService {
    Workout addWorkout(AddWorkoutRequest request);
    Workout getWorkoutById(Long id) throws WorkoutNotFoundException;
    Workout updateWorkout(Workout workout) throws WorkoutNotFoundException;
    void deleteWorkoutById(Long id) throws WorkoutNotFoundException;
    List<Workout> getAllWorkouts();
    List<Workout> getWorkoutsByName(String name); //
    List<Workout> getWorkoutsByCategory(String category); //
    List<Workout> getWorkoutsByNameAndCategory(String name, String category);
    int countWorkoutsByCategory(String category);
    List<WorkoutDTO> getConvertedWorkouts(List<Workout> workouts);
    WorkoutDTO convertToDto(Workout workout);
    // TODO:
    //  [1] Get workouts by User
    //  [2] Get workouts By User and Category
    //  [3] Count workouts By User
}