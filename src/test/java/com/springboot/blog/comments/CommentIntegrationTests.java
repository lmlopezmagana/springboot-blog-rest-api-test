package com.springboot.blog.comments;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.Assert.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test"})
@Testcontainers
@Sql(value = "classpath:import-posts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:import-comments.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:delete-comments.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CommentIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
            .withUsername("postgres")
            .withPassword("12345678")
            .withDatabaseName("test");

    @BeforeEach
    void setup(){
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    void getCommentsByPostIdWithValidIdTest(){
        // No es necesario usar "exchange" porque no devuelve ResponseEntity
        // y por tanto devuelve un 200 OK aunque no se haya encontrado nada
        Comment[] comments = restTemplate.getForObject("/api/v1/posts/{postId}/comments", Comment[].class, 1);
        Assertions.assertEquals(5, comments.length);
    }

    @Test
    void createCommentValidPostIdTest(){
        CommentDto c = new CommentDto();
        c.setId(1L);
        c.setName("name");

        ResponseEntity<CommentDto> response = restTemplate.exchange("/api/v1/posts/{postId}/comments",
                HttpMethod.POST, new HttpEntity<>(c), CommentDto.class, 1);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
