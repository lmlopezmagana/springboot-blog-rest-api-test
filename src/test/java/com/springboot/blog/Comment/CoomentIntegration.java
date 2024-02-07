package com.springboot.blog.Comment;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.*;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test"})
@Testcontainers
@Sql(value = "classpath:import-integration-comment.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CoomentIntegration {

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

    String adminToken;
    String userToken;

    CommentDto commentDto =  new CommentDto();


    @BeforeEach
    void setUp(){
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Authentication auth = new UsernamePasswordAuthenticationToken("lacabra_7","lacabra", authorities);
        adminToken = jwtProvider.generateToken(auth);
        Collection<GrantedAuthority> authorities2 = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth2 = new UsernamePasswordAuthenticationToken("ToRechulon","1234", authorities2);
        userToken = jwtProvider.generateToken(auth2);

       commentDto.setName("angel");
       commentDto.setBody("bodyyyyyyyyyyyyyyy");
       commentDto.setEmail("angel@gmail");
       commentDto.setId(1L);
    }

    @Test
    void createComment(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);
        HttpEntity<Object> requestBodyHeaders = new HttpEntity<>(commentDto, headers);
        ResponseEntity<CommentDto> response = testRestTemplate.exchange("/api/v1/posts/"+1000L+"/comments", HttpMethod.POST, requestBodyHeaders, CommentDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    @Test
    void getCommentByIdPostTest(){
        CommentDto commentDtos = testRestTemplate.exchange("/api/v1/posts/"+1000L+"/comments",HttpMethod.GET,CommentDto);

        assertEquals(1,commentDtos);
    }
}
