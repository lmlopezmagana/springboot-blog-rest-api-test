package com.springboot.blog.controller;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.springboot.blog.payload.RegisterDto;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@Sql(value = "classpath:sql/data.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AuthControllerIntegration {

    @LocalServerPort
    private int port;



    @Test
    public void authRegister_thenReturnCreated(){

        port = 8080;

        TestRestTemplate restTemplate = new TestRestTemplate();

        RegisterDto registerDto = new RegisterDto("Roberto","krobert151","robertorebolledo151@gmail.com","lagartoMolon34#");

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:"+port+"/api/auth/register",registerDto, String.class);

        System.out.println("Response Body: " + response.getBody());

        Assertions.assertEquals(201,response.getStatusCode().value());


    }

}