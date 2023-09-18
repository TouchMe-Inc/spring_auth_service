package by.touchme.authservice.controller;

import by.touchme.authservice.dto.BaseLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class AuthControllerIntegrationTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    final String URL = "/v1/auth";
    final String DOC_IDENTIFIER = "auth/{methodName}";

    @DisplayName("Integration test for AuthController.login")
    @Test
    void loginAsUser() throws Exception {
        BaseLoginDto baseLoginDto = new BaseLoginDto();
        baseLoginDto.setUsername("user");
        baseLoginDto.setPassword("12345678");

        this.mockMvc
                .perform(
                        post(URL + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(baseLoginDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(DOC_IDENTIFIER));
    }

    @DisplayName("Integration test for AuthController.login")
    @Test
    void loginAsAdmin() throws Exception {
        BaseLoginDto baseLoginDto = new BaseLoginDto();
        baseLoginDto.setUsername("admin");
        baseLoginDto.setPassword("12345678");

        this.mockMvc
                .perform(
                        post(URL + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(baseLoginDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(DOC_IDENTIFIER));
    }
}