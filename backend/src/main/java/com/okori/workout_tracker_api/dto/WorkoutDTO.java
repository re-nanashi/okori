package com.okori.workout_tracker_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class WorkoutDTO {
    private Long id;
    private String name;
    private String category;
    private String description;
    private List<ExerciseDTO> exercises;
    private List<WorkoutScheduleDTO> workoutSchedules;
}