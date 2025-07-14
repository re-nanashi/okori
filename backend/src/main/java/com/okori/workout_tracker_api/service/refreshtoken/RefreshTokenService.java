package com.okori.workout_tracker_api.service.refreshtoken;

import com.okori.workout_tracker_api.entity.RefreshToken;
import com.okori.workout_tracker_api.exceptions.ResourceNotFoundException;
import com.okori.workout_tracker_api.repository.RefreshTokenRepository;
import com.okori.workout_tracker_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService implements IRefreshTokenService {
    @Value("${jwt.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public RefreshToken getToken(String tokenString) throws ResourceNotFoundException {
        return refreshTokenRepository
                .findByToken(tokenString)
                .orElseThrow(() -> new ResourceNotFoundException("Queried token does not exists."));
    }

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        var token = new RefreshToken();
        token.setUser(userRepository.findById(userId).get());
        token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        token.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(token);
    }

    @Override
    public boolean isTokenExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }

    @Override
    public void deleteToken(RefreshToken token ) throws ResourceNotFoundException {
        refreshTokenRepository.findByToken(token.getToken())
                .ifPresentOrElse(refreshTokenRepository::delete,
                        () -> {throw new ResourceNotFoundException("Refresh token not found.");});
    }
}