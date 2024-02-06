package com.springboot.blog.Post;

import com.springboot.blog.entity.Post;
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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test"})
@Testcontainers
@Sql(value = "classpath:import-integration.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PostIntegrationTest {

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
    PostDto postDto = new PostDto();
    @BeforeEach
    void setUp(){
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Authentication auth = new UsernamePasswordAuthenticationToken("lacabra_7","lacabra", authorities);
        userToken = jwtProvider.generateToken(auth);

        postDto.setTitle("title");
        postDto.setDescription("description");
        postDto.setContent("contenidos guapos");
        postDto.setComments(Set.of());
        postDto.setCategoryId(1L);
        postDto.setId(1);
    }

    @Test
    void whenCreatePost_then201(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(userToken);
        HttpEntity<Object> requestBodyHeaders = new HttpEntity<>(postDto, headers);
        ResponseEntity<PostDto> response = testRestTemplate.exchange("/api/posts", HttpMethod.POST, requestBodyHeaders, PostDto.class, 300);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void whenGetAllPost_then200(){
        PostResponse postResponse = testRestTemplate.getForObject("/api/posts", PostResponse.class);
        assertEquals(1, postResponse.getContent().size());
    }
    @Test
    void whenGetPostById_then404(){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        HttpEntity<Object> responseHeaders = new HttpEntity<>(headers);
        ResponseEntity<PostDto> response = testRestTemplate.exchange("/api/posts/{id}", HttpMethod.GET, responseHeaders, PostDto.class, 300);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
