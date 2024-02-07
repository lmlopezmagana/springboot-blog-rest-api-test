package com.springboot.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.payload.CategoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class CategoryControllerIntegration {

    @LocalServerPort
    private int port;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    @Sql("classpath:delete-data.sql")
    public void setup() {
    }

    @Test
    @Sql("classpath:insert-data.sql")
    public void findCategoryById_thenReturnOk() throws Exception {

        Long idCategory = 1L;

        TestRestTemplate testRestTemplate = new TestRestTemplate();

        ResponseEntity<CategoryDto> response = testRestTemplate.getForEntity("http://localhost:" + port + "/api/v1/categories/" + idCategory, CategoryDto.class);
        assertEquals(200,response.getStatusCode().value());
        assertEquals("Teal",response.getBody().getName());

    }

    @Test
    public void findCategoryById_thenNotFound() throws Exception {

        Long idCategory = 1L;

        TestRestTemplate testRestTemplate = new TestRestTemplate();

        ResponseEntity<CategoryDto> response = testRestTemplate.getForEntity("http://localhost:" + port + "/api/v1/categories/" + idCategory, CategoryDto.class);
        assertEquals(404,response.getStatusCode().value());

    }




}
