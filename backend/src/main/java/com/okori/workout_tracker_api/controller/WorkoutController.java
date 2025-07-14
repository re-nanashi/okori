package com.okori.workout_tracker_api.controller;

import com.okori.workout_tracker_api.dto.WorkoutDTO;
import com.okori.workout_tracker_api.entity.User;
import com.okori.workout_tracker_api.entity.Workout;
import com.okori.workout_tracker_api.exceptions.ResourceNotFoundException;
import com.okori.workout_tracker_api.exceptions.UnauthorizedAccessException;
import com.okori.workout_tracker_api.request.AddWorkoutRequest;
import com.okori.workout_tracker_api.request.WorkoutUpdateRequest;
import com.okori.workout_tracker_api.response.ApiResponse;
import com.okori.workout_tracker_api.service.user.UserService;
import com.okori.workout_tracker_api.service.workout.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users/{userId}/workouts")
public class WorkoutController {
    @Autowired
    private WorkoutService workoutService;
    @Autowired
    private UserService userService;

    @GetMapping("/{workoutId}")
    public ResponseEntity<ApiResponse> getWorkoutById(@PathVariable Long userId, @PathVariable Long workoutId) {
        try {
            User user = userService.getAuthenticatedUser();
            Workout workout = workoutService.getWorkoutById(workoutId, user);
            WorkoutDTO workoutDTO = workoutService.convertToDto(workout);
            return  ResponseEntity.ok(new ApiResponse("Success", workoutDTO));
        } catch (ResourceNotFoundException | UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addWorkout(@PathVariable Long userId, @RequestBody AddWorkoutRequest workoutRequest) {
        try {
            User user = userService.getAuthenticatedUser();
            Workout newWorkout = workoutService.addWorkout(workoutRequest, user); // TODO: workoutRequest should be validated before adding to the repository
            WorkoutDTO workoutDto = workoutService.convertToDto(newWorkout);
            return ResponseEntity.ok(new ApiResponse("Add workout success!", workoutDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    /**
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
    */
}