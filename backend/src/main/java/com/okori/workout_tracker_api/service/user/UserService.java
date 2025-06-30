package com.okori.workout_tracker_api.service.user;

import com.okori.workout_tracker_api.dto.UserDTO;
import com.okori.workout_tracker_api.entity.User;
import com.okori.workout_tracker_api.exceptions.ResourceNotFoundException;
import com.okori.workout_tracker_api.repository.UserRepository;
import com.okori.workout_tracker_api.request.CreateUserRequest;
import com.okori.workout_tracker_api.request.UserUpdateRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByUsername(request.getUsername()))
                .map(req -> {
                    User user = new User(
                            request.getFirstName(),
                            request.getLastName(),
                            request.getUsername(),
                            request.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("Oops!" +request.getUsername() +" already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository
                .findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found. Update aborted."));
    }

    @Override
    public void deleteUser(Long userId) {
        // Verify if the userId exists in the database
        userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found. Deletion aborted."));

        // Delete the workout if found
        userRepository.deleteById(userId);
    }

    @Override
    public UserDTO convertUserToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }
}