package com.springboot.blog.service;

import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void login_ValidCredentials_ReturnToken() {
        LoginDto loginDto = new LoginDto("username", "password");
        String expectedToken = "generated-token";
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword());

        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(authentication);

        Mockito.when(jwtTokenProvider.generateToken(Mockito.any()))
                .thenReturn(expectedToken);

        String actualToken = authService.login(loginDto);

        assertEquals(expectedToken, actualToken);
    }

    @Test
    void login_InvalidCredentials_ThrowsException() {
        LoginDto loginDto = new LoginDto("invalidUsername", "invalidPassword");

        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenThrow(new AuthenticationServiceException("Invalid credentials"));

        assertThrows(AuthenticationServiceException.class, () -> authService.login(loginDto));
    }

    @Test
    void register() {
    }
}