package com.okori.workout_tracker_api.security.user;

import com.okori.workout_tracker_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class UserSecurity implements AuthorizationManager<RequestAuthorizationContext> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext ctx) {
        Long userId = Long.parseLong(ctx.getVariables().get("userId")); // userID is from URI path
        Authentication authentication = (Authentication) authenticationSupplier.get();
        return new AuthorizationDecision(hasUserId(authentication, userId));
    }

    // What does do
    private boolean hasUserId(Authentication authentication, Long userId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        return userRepository.findByEmail(authentication.getName())
                .map(user -> user.getId() == userId)
                .orElse(false);
    }
}