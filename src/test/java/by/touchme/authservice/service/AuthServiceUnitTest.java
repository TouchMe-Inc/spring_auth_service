package by.touchme.authservice.service;

import by.touchme.authservice.dto.BaseLoginDto;
import by.touchme.authservice.dto.JwtDto;
import by.touchme.authservice.entity.Role;
import by.touchme.authservice.entity.User;
import by.touchme.authservice.provider.JwtProvider;
import by.touchme.authservice.repository.UserRepository;
import by.touchme.authservice.service.impl.AuthServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ActiveProfiles(profiles = "test")
@ExtendWith(MockitoExtension.class)
public class AuthServiceUnitTest {
    @Autowired
    ObjectMapper objectMapper;

    @InjectMocks
    AuthServiceImpl authService;

    @Mock
    UserRepository userRepository;

    @Mock
    JwtProvider jwtProvider;

    @DisplayName("JUnit test for AuthService.login without body")
    @Test
    void login() {
        final String username = "user";
        final String accessToken = "accessToken";
        final String refreshToken = "refreshToken";

        BaseLoginDto baseLoginDto = new BaseLoginDto();
        baseLoginDto.setUsername(username);
        baseLoginDto.setPassword("12345678");

        User user = new User(1L, username, "12345678", Set.of(Role.ROLE_USER));

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(jwtProvider.generateAccessToken(user)).thenReturn(accessToken);
        when(jwtProvider.generateRefreshToken(user)).thenReturn(refreshToken);

        JwtDto jwtDto = authService.login(baseLoginDto);

        Assertions.assertThat(jwtDto.getAccessToken()).isSameAs(accessToken);
        Assertions.assertThat(jwtDto.getRefreshToken()).isSameAs(refreshToken);
    }
}
