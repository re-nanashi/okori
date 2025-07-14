package com.okori.workout_tracker_api.controller;

import com.okori.workout_tracker_api.dto.UserDTO;
import com.okori.workout_tracker_api.entity.RefreshToken;
import com.okori.workout_tracker_api.entity.User;
import com.okori.workout_tracker_api.exceptions.AlreadyExistsException;
import com.okori.workout_tracker_api.exceptions.ResourceNotFoundException;
import com.okori.workout_tracker_api.request.CreateUserRequest;
import com.okori.workout_tracker_api.request.LoginRequest;
import com.okori.workout_tracker_api.response.ApiResponse;
import com.okori.workout_tracker_api.response.JwtResponse;
import com.okori.workout_tracker_api.security.jwt.JwtUtil;
import com.okori.workout_tracker_api.security.user.OkoriUserDetails;
import com.okori.workout_tracker_api.service.refreshtoken.RefreshTokenService;
import com.okori.workout_tracker_api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private JwtUtil jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody CreateUserRequest createUserRequest) {
        try {
            User user = userService.createUser(createUserRequest);
            UserDTO userDTO = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User registered successfully.", userDTO));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            OkoriUserDetails userDetails = (OkoriUserDetails) authentication.getPrincipal();
            String accessToken = jwtUtils.generateToken(userDetails.getUsername());

            User dbUser = userService.getUserById(userDetails.getId());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(dbUser.getId());

            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(),
                    Map.of( "accessToken", accessToken,
                            "refreshToken", refreshToken.getToken()));
            return ResponseEntity.ok(new ApiResponse("Login Successful!", jwtResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logoutUser(@RequestBody Map<String, String> payload) {
        try {
            String requestToken = payload.get("refreshToken");

            if (requestToken == null || requestToken.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Refresh token is required.", null));
            }

            RefreshToken refreshToken = refreshTokenService.getToken(requestToken);
            refreshTokenService.deleteToken(refreshToken);

            return ResponseEntity.ok(new ApiResponse("Logged out successfully.", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage() + "Invalid refresh token.", null));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refreshToken(@RequestBody Map<String, String> payload) {
        try {
            String requestToken = payload.get("refreshToken");
            RefreshToken token = refreshTokenService.getToken(requestToken);
            if (refreshTokenService.isTokenExpired(token)) {
                refreshTokenService.deleteToken(token);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Refresh token expired. Please login again.", null));
            }
            String newJwtToken = jwtUtils.generateToken(token.getUser().getEmail());
            JwtResponse jwtResponse = new JwtResponse(token.getUser().getId(), Map.of("token", newJwtToken));
            return ResponseEntity.ok(new ApiResponse("Token refreshed.", jwtResponse));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage() + "Invalid refresh token.", null));
        }
    }
}