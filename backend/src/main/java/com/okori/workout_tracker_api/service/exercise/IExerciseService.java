package com.okori.workout_tracker_api.service.exercise;

import com.okori.workout_tracker_api.entity.Exercise;
import com.okori.workout_tracker_api.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IExerciseService {
    Exercise addExercise(Exercise exercise);
    Exercise getExerciseById(Long id) throws ResourceNotFoundException;
    Exercise updateExercise(Exercise exercise) throws ResourceNotFoundException;
    void deleteExerciseById(Long id) throws ResourceNotFoundException;
    List<Exercise> getAllExercises();
    List<Exercise> getExercisesByName(String name); //
    List<Exercise> getExercisesByType(String type); //
    List<Exercise> getExercisesByNameAndType(String name, String type);
    List<Exercise> getExercisesByWorkoutId(Long workoutId);
    List<Exercise> getExercisesByWorkoutIdAndType(Long workoutId, String type);
    int countExercisesByType(String type);
    int countExerciseByWorkoutId(Long workoutId);
}