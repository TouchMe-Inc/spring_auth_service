package by.touchme.authservice.service.impl;

import by.touchme.authservice.dto.BaseLoginDto;
import by.touchme.authservice.dto.JwtDto;
import by.touchme.authservice.dto.RefreshDto;
import by.touchme.authservice.entity.User;
import by.touchme.authservice.exception.AuthException;
import by.touchme.authservice.provider.JwtProvider;
import by.touchme.authservice.repository.UserRepository;
import by.touchme.authservice.service.AuthService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public JwtDto login(@NonNull BaseLoginDto baseLoginDto) {
        final String username = baseLoginDto.getUsername();
        final User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new AuthException("User with username = " + username + " not found"));

        // Password is not encrypted.
        if (!user.confirmPassword(baseLoginDto.getPassword())) {
            throw new AuthException("Incorrect password");
        }

        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);

        return new JwtDto(accessToken, refreshToken);
    }

    @Override
    public JwtDto refresh(@NonNull RefreshDto requestRefreshDto) {
        final String refreshToken = requestRefreshDto.getRefreshToken();

        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            throw new AuthException("Incorrect refresh token");
        }

        final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
        final String username = claims.getSubject();

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new AuthException("User with username = " + username + " not found"));

        final String accessToken = jwtProvider.generateAccessToken(user);
        final String newRefreshToken = jwtProvider.generateRefreshToken(user);

        return new JwtDto(accessToken, newRefreshToken);
    }
}
