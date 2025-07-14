package com.okori.workout_tracker_api.controller;

import com.okori.workout_tracker_api.dto.UserDTO;
import com.okori.workout_tracker_api.entity.User;
import com.okori.workout_tracker_api.exceptions.ResourceNotFoundException;
import com.okori.workout_tracker_api.request.UserUpdateRequest;
import com.okori.workout_tracker_api.response.ApiResponse;
import com.okori.workout_tracker_api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDTO userDTO = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success", userDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // TODO: User can update password
    // ROLE_ADMIN can update user and it's password
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId) {
        try {
            User user = userService.updateUser(request, userId);
            UserDTO userDTO = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Create user success", userDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete user success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}