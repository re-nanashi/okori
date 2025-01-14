package com.okori.workout_tracker_api.dto;

import lombok.Data;

@Data
public class ExerciseDTO {
    private String name;
    private String type;
    private String description;
}