package com.okori.workout_tracker_api.data;

import com.okori.workout_tracker_api.entity.Role;
import com.okori.workout_tracker_api.entity.User;
import com.okori.workout_tracker_api.entity.UserRole;
import com.okori.workout_tracker_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Transactional
@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Initializes data only if roles and users are missing
        Set<String> defaultRoles = Set.of(
                UserRole.ROLE_ADMIN.toString(),
                UserRole.ROLE_USER.toString()
        );
        initializeDefaultRoles(defaultRoles);
        initializeAdministrator();
        initializeDefaultUser();
    }

    private void initializeDefaultRoles(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new)
                .forEach(roleRepository::save);
    }

    private void initializeDefaultUser() {
        String defaultEmail = "adam@email.com";
        if (userRepository.existsByEmail(defaultEmail)) {
            return;
        }
        User user = new User();
        user.setFirstName("Adam");
        user.setLastName("User");
        user.setEmail(defaultEmail);
        user.setPassword(passwordEncoder.encode("12345689"));
        Role userRole = roleRepository.findByName(UserRole.ROLE_USER.toString()).get();
        user.setRoles(Set.of(userRole));
        userRepository.save(user);

        System.out.println("Default user " + user.getFirstName() + " was created.");
    }

    private void initializeAdministrator() {
        String defaultEmail = "god@email.com";
        if (userRepository.existsByEmail(defaultEmail)) {
            return;
        }
        User user = new User();
        user.setFirstName("God");
        user.setLastName("Admin");
        user.setEmail(defaultEmail);
        user.setPassword(passwordEncoder.encode("12345689"));
        Role adminRole = roleRepository.findByName(UserRole.ROLE_ADMIN.toString()).get();
        user.setRoles(Set.of(adminRole));
        userRepository.save(user);

        System.out.println("Admin " + user.getFirstName() + " was created.");
    }
}