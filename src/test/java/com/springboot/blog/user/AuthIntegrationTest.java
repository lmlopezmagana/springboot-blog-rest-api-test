package com.springboot.blog.user;


import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.security.JwtTokenProvider;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test"})
@Testcontainers
@Sql(value = "classpath:import-integration.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

public class AuthIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    JwtTokenProvider jwtProvider;

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
            .withUsername("user")
            .withPassword("user")
            .withDatabaseName("test");

    @LocalServerPort
    int port;

    String userToken;
    LoginDto loginDto = new LoginDto();

    @BeforeEach
    void  setUp(){
        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken("ToRechulon", "1234", authorities);

        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        token = jwtProvider.generateToken(aut);
    }

    @Test
    void validLogin_then200(){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        HttpEntity<Object> responseHeaders = new HttpEntity<>(loginDto, headers);
        ResponseEntity<JWTAuthResponse> response = testRestTemplate.exchange("/api/auth/login", HttpMethod.POST, responseHeaders, JWTAuthResponse.class);
        System.out.printf(response.toString());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
