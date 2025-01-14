package com.okori.workout_tracker_api.service.exercise;

import com.okori.workout_tracker_api.entity.Exercise;
import com.okori.workout_tracker_api.exceptions.ExerciseNotFoundException;
import com.okori.workout_tracker_api.repository.ExerciseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ExerciseService implements IExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public Exercise addExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public Exercise getExerciseById(Long id) throws ExerciseNotFoundException {
        return exerciseRepository
                .findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise not found."));
    }

    @Override
    public Exercise updateExercise(Exercise exercise) throws ExerciseNotFoundException {
        // Get the id then verify if the id exists in the database
        exerciseRepository
                .findById(exercise.getId())
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise not found. Update aborted."));
        // Update the exercise if found
        return exerciseRepository.save(exercise);
    }

    @Override
    public void deleteExerciseById(Long id) throws ExerciseNotFoundException {
        // Verify if the exercise id exists in the database
        exerciseRepository
                .findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise not found. Deletion aborted."));
        // Delete the exercise if found
        exerciseRepository.deleteById(id);
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @Override
    public List<Exercise> getExercisesByName(String name) {
        return exerciseRepository.findAllByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Exercise> getExercisesByType(String type) {
        return exerciseRepository.findAllByType(type);
    }

    @Override
    public List<Exercise> getExercisesByNameAndType(String name, String type) {
        return exerciseRepository.findAllByNameContainingIgnoreCaseAndType(name, type);
    }

    @Override
    public List<Exercise> getExercisesByWorkoutId(Long workoutId) {
        return exerciseRepository
                .findAll()
                .stream()
                .filter(exercise -> exercise.getWorkout().getId() == workoutId).toList();
    }

    @Override
    public List<Exercise> getExercisesByWorkoutIdAndType(Long workoutId, String type) {
        return getExercisesByWorkoutId(workoutId)
                .stream()
                .filter(exercise -> exercise.getType() == type).toList();
    }

    @Override
    public int countExercisesByType(String type) {
        return exerciseRepository.findAllByType(type).size();
    }

    @Override
    public int countExerciseByWorkoutId(Long workoutId) {
        return getExercisesByWorkoutId(workoutId).size();
    }
}