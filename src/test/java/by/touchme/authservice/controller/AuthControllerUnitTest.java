package by.touchme.authservice.controller;

import by.touchme.authservice.dto.BaseLoginDto;
import by.touchme.authservice.dto.RefreshDto;
import by.touchme.authservice.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
public class AuthControllerUnitTest {

    final String URL_LOGIN = "/v1/auth/login";
    final String URL_REFRESH = "/v1/auth/refresh";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @DisplayName("JUnit test for AuthController.login without body")
    @Test
    void loginWithoutBody() throws Exception {
        mockMvc
                .perform(
                        post(URL_LOGIN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for AuthController.login without username")
    @Test
    void loginWithoutUsername() throws Exception {
        BaseLoginDto baseLoginDto = new BaseLoginDto();
        baseLoginDto.setPassword("password");

        mockMvc
                .perform(
                        post(URL_LOGIN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(baseLoginDto))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for AuthController.login without password")
    @Test
    void loginWithoutPassword() throws Exception {
        BaseLoginDto baseLoginDto = new BaseLoginDto();
        baseLoginDto.setUsername("username");

        mockMvc
                .perform(
                        post(URL_LOGIN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(baseLoginDto))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for AuthController.login")
    @Test
    void login() throws Exception {
        BaseLoginDto baseLoginDto = new BaseLoginDto();
        baseLoginDto.setUsername("username");
        baseLoginDto.setPassword("password");

        mockMvc
                .perform(
                        post(URL_LOGIN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(baseLoginDto))
                )
                .andDo(print())
                .andExpect(status().isOk());

        verify(authService, times(1)).login(baseLoginDto);
    }

    @DisplayName("JUnit test for AuthController.refresh without body")
    @Test
    void refreshWithoutBody() throws Exception {
        mockMvc
                .perform(
                        post(URL_REFRESH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for AuthController.refresh without refresh token")
    @Test
    void refreshWithoutRefreshToken() throws Exception {
        RefreshDto refreshDto = new RefreshDto();

        mockMvc
                .perform(
                        post(URL_REFRESH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(refreshDto))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for AuthController.refresh")
    @Test
    void refresh() throws Exception {
        RefreshDto refreshDto = new RefreshDto();
        refreshDto.setRefreshToken("refreshToken");

        mockMvc
                .perform(
                        post(URL_REFRESH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(refreshDto))
                )
                .andDo(print())
                .andExpect(status().isOk());

        verify(authService, times(1)).refresh(refreshDto);
    }
}
