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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/users/{userId}/workouts")
public class WorkoutController {
    @Autowired
    private WorkoutService workoutService;
    @Autowired
    private UserService userService;

    // TODO: Since we now have a user and we need to verify first if the user is accessing the data, we need to check if
    //  the one accessing the data is indeed the user.
    //  Task: Implement getWorkoutById() using User as parameter
    // Still kahit makaaccess tayo dito, dapat we still have the context meaning we should still know what is the UserId
    // Kunwari naaccess natin yung endpoint na'to
    // Paano natin naccess?
    // we have the currently logged in user == userId(URI)
    // let's say we have user1(id: 1) access users/1/workouts/1
    //                   as long as we are access users/1/ and the user is actually 1 then all good
    //                  what if we try to access user2(id:2) this endpoint
    // SO we have two conditions if user == userID(URI) what will happen?
    //                      so we will have access to this endpoint
    //                      then by the workoutId
    // What happened is although
    // if user != userId(URI) that was accessed
    @GetMapping("/{workoutId}")
    public ResponseEntity<ApiResponse> getWorkoutById(@PathVariable Long userId, @PathVariable Long workoutId) {
        try {
            // TODO: is this really the currently logged User? or it's just because of the path?
            //  By logic, since we can't access this controller method unless we access the URL with the userID of the currently logged-in user,
            //  this means that we have two options, either get the userID using what's included in the URL or through getPrincipal() method then using the userService to find the current
            //  user by username. Second option feels a lot more code needs to be written.
            User currentlyLoggedUser = userService.getUserById(userId);
            Workout workout = workoutService.getWorkoutById(workoutId, currentlyLoggedUser);
            WorkoutDTO workoutDTO = workoutService.convertToDto(workout);
            return  ResponseEntity.ok(new ApiResponse("Success", workoutDTO));
        } catch (ResourceNotFoundException | UnauthorizedAccessException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // TODO: should we use the userId in the RequestMapping of this controller or use getPrincipal?
    @PostMapping
    public ResponseEntity<ApiResponse> addWorkout(@PathVariable Long userId, @RequestBody AddWorkoutRequest workoutRequest) {
        try {
            User currentlyLoggedUser = userService.getUserById(userId);
            Workout newWorkout = workoutService.addWorkout(workoutRequest, currentlyLoggedUser); // TODO: workoutRequest should be validated before adding to the repository
            WorkoutDTO workoutDto = workoutService.convertToDto(newWorkout);
            return ResponseEntity.ok(new ApiResponse("Add workout success!", workoutDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
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