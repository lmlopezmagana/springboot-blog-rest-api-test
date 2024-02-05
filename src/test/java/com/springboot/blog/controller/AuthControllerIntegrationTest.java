package com.springboot.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import com.springboot.blog.payload.RegisterDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class AuthControllerIntegrationTest {

    @LocalServerPort
    //@Value("${local.server.port}")
    private int port;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    @Sql("classpath:delete-data.sql")
    public void setup() {
    }

    @Test
    @Sql("classpath:insert-data.sql")
    public void whenLoginRegisteredUser_thenReturnOk() throws Exception {

        LoginDto loginDto = new LoginDto("sdebeneditti1@icq.com", "aH5_V1Oar1");
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<JWTAuthResponse> response = testRestTemplate.postForEntity("http://localhost:"+port+"/api/auth/login", loginDto, JWTAuthResponse.class);
        System.out.println("Response Body: " + objectMapper.writeValueAsString(response.getBody()));
        assertEquals(200, response.getStatusCode().value());

        //comprobar algo mas con json path o con objectMapper
    }



    @Test
    public void authRegister_thenReturnCreated(){

        port = 8080;

        RegisterDto registerDto = new RegisterDto("Roberto","krobert151","robertorebolledo151@gmail.com","lagartoMolon34#");
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:"+port+"/api/auth/register",registerDto, String.class);

        System.out.println("Request Body: " + testRestTemplate.toString());
        System.out.println("Response Body: " + response.getBody());

        assertEquals(201,response.getStatusCode().value());


    }

}