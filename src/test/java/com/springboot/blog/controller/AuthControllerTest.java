package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AuthService authService;

    @InjectMocks
    AuthController authController;


    void login() {
    }

    //Marco Pertegal
    @Test
    void whenValidCredentialsThenReturnHttp201() throws Exception{
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("marco@gmail.com");
        registerDto.setUsername("Marco");
        registerDto.setPassword("12345");

        mockMvc.perform(post("/api/auth/register")
                                .content(objectMapper.writeValueAsString(registerDto))
                                .contentType(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isCreated());
    }
    //Marco Pertegal
    @Test
    void whenExistEmailThenReturnHttp400() throws Exception{
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("marco@gmail.com");
        registerDto.setUsername("Marco");
        registerDto.setPassword("12345");

        Mockito.doThrow(new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!."))
                .when(authService).register(Mockito.any(RegisterDto.class));

        mockMvc.perform(post("/api/auth/register")
                                .content(objectMapper.writeValueAsString(registerDto))
                                .contentType(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isBadRequest());

    }
    //Marco Pertegal
    @Test
    void whenExistUsernameThenReturnHttp400() throws Exception{
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("marco@gmail.com");
        registerDto.setUsername("Marco");
        registerDto.setPassword("12345");

        Mockito.doThrow(new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exists!."))
                .when(authService).register(Mockito.any(RegisterDto.class));

        mockMvc.perform(post("/api/auth/register")
                .content(objectMapper.writeValueAsString(registerDto))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

    }

}