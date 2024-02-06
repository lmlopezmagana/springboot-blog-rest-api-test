package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthService authService;

    @InjectMocks
    private AuthController authController;
    private LoginDto loginDto;
    private LoginDto loginDtoEmpty;

    @BeforeEach
    void setUp() {
        loginDto = new LoginDto("username", "password");
        loginDtoEmpty = new LoginDto("", "");
    }

    @Test
    void whenLoginIsValid_thenReturnHttp200() throws Exception {

        String token = "token";

        Mockito.when(authService.login(Mockito.any(LoginDto.class))).thenReturn(token);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .content(objectMapper.writeValueAsString(loginDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("token"));
    }

    @Test
    void whenLoginIsNull_thenReturnHttp400() throws Exception {
        String token = "token";

        Mockito.when(authService.login(Mockito.any(LoginDto.class))).thenReturn(token);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .content(objectMapper.writeValueAsString(null))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Disabled
    void register() {
    }
}