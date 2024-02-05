package com.springboot.blog.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.springboot.blog.payload.RegisterDto;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class AuthControllerIntegration {

    @LocalServerPort
    private int port;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    @Sql("classpath:delete-data.sql")
    public void setup() {
    }



    @Test
    public void authRegister_thenReturnCreated() throws JsonProcessingException {

        System.out.println(port);

        TestRestTemplate restTemplate = new TestRestTemplate();

        RegisterDto registerDto = new RegisterDto("Roberto","krobert151","robertorebolledo151@gmail.com","lagartoMolon34#");

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:"+port+"/api/auth/register",registerDto, String.class);

        System.out.println("Response Body: " + objectMapper.writeValueAsString(response.getBody()));

        Assertions.assertEquals(201,response.getStatusCode().value());


    }

}