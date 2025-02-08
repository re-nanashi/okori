package com.okori.workout_tracker_api.service.user;

import com.okori.workout_tracker_api.dto.UserDTO;
import com.okori.workout_tracker_api.entity.User;
import com.okori.workout_tracker_api.request.CreateUserRequest;
import com.okori.workout_tracker_api.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
    UserDTO convertUserToDto(User user);
    User getAuthenticatedUser();
}