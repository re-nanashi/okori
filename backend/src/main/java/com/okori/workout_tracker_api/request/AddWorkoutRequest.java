package com.okori.workout_tracker_api.request;

import com.okori.workout_tracker_api.dto.ExerciseDTO;
import com.okori.workout_tracker_api.dto.WorkoutScheduleDTO;

import java.util.List;

public class AddWorkoutRequest {
    private Long id;
    private String name;
    private String category;
    private String description;
    private List<ExerciseDTO> exercises;
    private List<WorkoutScheduleDTO> workoutSchedules;

    public AddWorkoutRequest() {}
    public AddWorkoutRequest(String name, String category, String description, List<ExerciseDTO> exercises, List<WorkoutScheduleDTO> workoutSchedules) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.exercises = exercises;
        this.workoutSchedules = workoutSchedules;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ExerciseDTO> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseDTO> exercises) {
        this.exercises = exercises;
    }

    public List<WorkoutScheduleDTO> getWorkoutSchedules() {
        return workoutSchedules;
    }

    public void setWorkoutSchedules(List<WorkoutScheduleDTO> workoutSchedules) {
        this.workoutSchedules = workoutSchedules;
    }
}