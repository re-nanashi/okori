package com.okori.workout_tracker_api.service.refreshtoken;

import com.okori.workout_tracker_api.entity.RefreshToken;
import com.okori.workout_tracker_api.exceptions.ResourceNotFoundException;

// TODO: Delete token method signature
public interface IRefreshTokenService {
    RefreshToken getToken(String tokenString) throws ResourceNotFoundException;
    RefreshToken createRefreshToken(Long userId);
    boolean isTokenExpired(RefreshToken token);
    void deleteToken(RefreshToken token);
}