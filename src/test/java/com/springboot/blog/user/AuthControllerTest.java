package com.springboot.blog.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.controller.AuthController;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AuthService authService;

    @InjectMocks
    AuthController authController;


    @Test
    void validLogin() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("1234");
        loginDto.setUsernameOrEmail("ToReshulon");

        String token = "tokenGuay";
        when(authService.login(any(LoginDto.class))).thenReturn(token);


        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(token));
    }

    @Test
    void invalidLogin() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("InvalidUser");
        loginDto.setPassword("InvalidPassword");

        doThrow(new BadCredentialsException("invalid login"))
                .when(authService).login(any(LoginDto.class));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void whenInvalidRegisterUserNameNull_thenReturns400AndErrorResult() throws Exception{
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername(null);
        registerDto.setEmail("user@example.com");
        registerDto.setPassword("password");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validRegister_thenReturns201AndCreatedResult() throws Exception{
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("Pedro");
        registerDto.setEmail("user@example.com");
        registerDto.setPassword("password");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenInvalidRegisterUserNameExists_thenReturns400AndErrorResult() throws Exception{
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("Pedro");
        registerDto.setEmail("user@example.com");
        registerDto.setPassword("password");

        doThrow(new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exists!."))
                .when(authService).register(any(RegisterDto.class));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenInvalidRegisterEmailExists_thenThrowsException() throws Exception {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("Pedro");
        registerDto.setEmail("user@example.com");
        registerDto.setPassword("password");

        doThrow(new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!."))
                .when(authService).register(any(RegisterDto.class));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isBadRequest());
    }


}
