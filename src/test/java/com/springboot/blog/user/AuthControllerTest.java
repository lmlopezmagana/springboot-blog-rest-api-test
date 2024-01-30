package com.springboot.blog.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.controller.AuthController;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    AuthService authService;

    @InjectMocks
    AuthController authController;

    @Test
    void whenInvalidRegister_thenReturns400AndErrorResult() throws Exception{
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername(null); // Invalid username
        registerDto.setEmail("user@example.com");
        registerDto.setPassword("password");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isBadRequest());
    }

}
