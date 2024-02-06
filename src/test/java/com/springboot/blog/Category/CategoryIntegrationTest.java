package com.springboot.blog.Category;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.security.JwtTokenProvider;
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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test"})
@Testcontainers
@Sql(value = "classpath:import-integration-category.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CategoryIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    JwtTokenProvider jwtProvider;
    @LocalServerPort
    int port;
    HttpHeaders header = new HttpHeaders();
    CategoryDto dto = new CategoryDto();
    String userToken;
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
            .withUsername("postgres")
            .withPassword("12345678")
            .withDatabaseName("myblog");
    @BeforeEach
    void setUp(){
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Authentication auth = new UsernamePasswordAuthenticationToken("AdminUsername","admin",authorities);
        userToken = jwtProvider.generateToken(auth);

        dto.setId(3L);
        dto.setName("Category name");
        dto.setDescription("Category description");
    }

    @Test
    void getCategories_200(){
        CategoryDto[] response = testRestTemplate.getForObject("/api/v1/categories", CategoryDto[].class);
        assertEquals(2, response.length);
    }

    @Test
    void getCategory_200(){
        HttpEntity<HttpHeaders> objectRequest = new HttpEntity<>(header);

        ResponseEntity<CategoryDto> response = testRestTemplate.exchange("/api/v1/categories/{id}",
                HttpMethod.GET, objectRequest, CategoryDto.class,1000);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category", response.getBody().getName());
        assertNotNull(response.getBody());
    }

    @Test
    void getCategory_404(){
        HttpEntity<HttpHeaders> objectRequest = new HttpEntity<>(header);

        ResponseEntity<CategoryDto> response = testRestTemplate.exchange("/api/v1/categories/{id}",
                HttpMethod.GET, objectRequest, CategoryDto.class,35);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void addCategory_201(){
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setBearerAuth(userToken);

        HttpEntity<CategoryDto> objectRequest = new HttpEntity<>(dto,header);

        ResponseEntity<CategoryDto> response = testRestTemplate.exchange("/api/v1/categories",
                HttpMethod.POST, objectRequest, CategoryDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }



}
