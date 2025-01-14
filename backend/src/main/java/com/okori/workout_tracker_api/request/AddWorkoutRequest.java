package com.okori.workout_tracker_api.request;

import com.okori.workout_tracker_api.dto.ExerciseDTO;
import com.okori.workout_tracker_api.dto.WorkoutScheduleDTO;
import lombok.Data;

import java.util.List;

@Data
public class AddWorkoutRequest {
      private String name;
      private String category;
      private String description;
      private List<ExerciseDTO> exercises;
      private List<WorkoutScheduleDTO> workoutSchedules;
}