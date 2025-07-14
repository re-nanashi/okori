package com.okori.workout_tracker_api.controller;

import com.okori.workout_tracker_api.dto.UserDTO;
import com.okori.workout_tracker_api.entity.User;
import com.okori.workout_tracker_api.repository.UserRepository;
import com.okori.workout_tracker_api.response.ApiResponse;
import com.okori.workout_tracker_api.security.user.OkoriUserDetailsService;
import com.okori.workout_tracker_api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/test")
public class TestController {
    @Autowired
    private OkoriUserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> userAccess() {
        try {
            User authenticatedUser = userService.getAuthenticatedUser();
            UserDTO userDTO = new UserDTO(
                    authenticatedUser.getId(),
                    authenticatedUser.getFirstName(),
                    authenticatedUser.getLastName(),
                    authenticatedUser.getEmail());
            return ResponseEntity.ok(new ApiResponse("Success", userDTO));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}