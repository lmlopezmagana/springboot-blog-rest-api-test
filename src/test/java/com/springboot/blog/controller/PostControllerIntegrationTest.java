package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.PostService;
import com.springboot.blog.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
class PostControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private MultiValueMap<String, String> headers;
    private String userToken;
    private String adminToken;
    private Long idPost;

    @Test
    void createPost() {
    }

    @Test
    void getAllPosts() {
    }

    //Sebastián Millán
    @Test
    void getPostById() {
    }

    @BeforeEach
    void setUp(){
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        idPost=1L;
        User admin = new User(1L,"Micah Eakle","meakle0","meakle0@newsvine.com", passwordEncoder.encode("kJ3(1SY6uMM"), Set.of(new Role((short)1,"ADMIN")));
        User user = new User(51L, "Danny Girkins", "dgirkins1e", "dgirkins1e@bing.com", passwordEncoder.encode("lE2,%W?IAA"), Set.of(new Role((short)2,"USER")));
        System.out.println(admin.getPassword());

        userToken=jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken(
                user.getUsername(),user.getRoles()));
        //adminToken=jwtTokenProvider.generateToken();
        System.out.println(userToken);
        headers=new LinkedMultiValueMap<>();
        headers.add("content-type","application/json");
        headers.add("Authorization","Bearer "+userToken);
    }

    @Test
    @Sql("classpath:data-integration.sql")
    void whenIdExistsAndUserRole_thenGetPostDtoAndReturn200() throws Exception{
        //when(postService.getPostById(1L));
        ResponseEntity<PostDto> response = testRestTemplate.exchange("http://localhost/"+port+"/api/posts/"+idPost,
                HttpMethod.GET,new HttpEntity<>(headers), PostDto.class);

        assertEquals("200", response.getStatusCode());
    }

    @Test
    //@WithMockUser()
    void whenIdExistsAndWithoutRole_thenGetPostDtoAndReturn200() throws Exception{
    }

    @Test
    void updatePost() {
    }

    @Test
    void deletePost() {
    }

    @Test
    void getPostsByCategory() {
    }
}