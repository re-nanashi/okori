package com.okori.workout_tracker_api.controller;

import com.okori.workout_tracker_api.dto.WorkoutDTO;
import com.okori.workout_tracker_api.entity.Workout;
import com.okori.workout_tracker_api.exceptions.ResourceNotFoundException;
import com.okori.workout_tracker_api.request.AddWorkoutRequest;
import com.okori.workout_tracker_api.request.WorkoutUpdateRequest;
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

    @GetMapping("/{workoutId}/workout")
    public ResponseEntity<ApiResponse> getWorkoutById(@PathVariable Long workoutId) {
        try {
            Workout workout = workoutService.getWorkoutById(workoutId);
            WorkoutDTO workoutDTO = workoutService.convertToDto(workout);
            return  ResponseEntity.ok(new ApiResponse("Success", workoutDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addWorkout(@RequestBody AddWorkoutRequest workoutRequest) {
        try {
            Workout newWorkout = workoutService.addWorkout(workoutRequest); // TODO: workoutRequest should be validated before adding to the repository
            WorkoutDTO workoutDto = workoutService.convertToDto(newWorkout);
            return ResponseEntity.ok(new ApiResponse("Add workout success!", workoutDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{workoutId}/update")
    public  ResponseEntity<ApiResponse> updateWorkout(@RequestBody WorkoutUpdateRequest request, @PathVariable Long workoutId) {
        try {
            // TODO: WorkoutUpdateRequest
            Workout workout = workoutService.updateWorkout(request, workoutId);
            WorkoutDTO workoutDto = workoutService.convertToDto(workout);
            return ResponseEntity.ok(new ApiResponse("Update workout success!", workoutDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{workoutId}/delete")
    public ResponseEntity<ApiResponse> deleteWorkout(@PathVariable Long workoutId) {
        try {
            workoutService.deleteWorkoutById(workoutId);
            return ResponseEntity.ok(new ApiResponse("Delete workout success!", workoutId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}