package com.okori.workout_tracker_api.controller;

import com.okori.workout_tracker_api.dto.WorkoutDTO;
import com.okori.workout_tracker_api.entity.Workout;
import com.okori.workout_tracker_api.request.AddWorkoutRequest;
import com.okori.workout_tracker_api.response.ApiResponse;
import com.okori.workout_tracker_api.service.workout.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/workouts")
public class WorkoutController {
    @Autowired
    private WorkoutService workoutService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllWorkouts() {
        List<Workout> workouts = workoutService.getAllWorkouts();
        List<WorkoutDTO> convertedWorkouts = workoutService.getConvertedWorkouts(workouts);
        return ResponseEntity.ok(new ApiResponse("Success", convertedWorkouts));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addWorkout(@RequestBody AddWorkoutRequest workoutRequest) {
        try {
            Workout newWorkout = workoutService.addWorkout(workoutRequest);
            WorkoutDTO workoutDto = workoutService.convertToDto(newWorkout);
            return ResponseEntity.ok(new ApiResponse("Success", workoutDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
