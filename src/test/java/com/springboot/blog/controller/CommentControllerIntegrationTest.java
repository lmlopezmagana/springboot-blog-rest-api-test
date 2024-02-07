package com.springboot.blog.controller;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.security.JwtTokenProvider;
import io.jsonwebtoken.lang.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@Sql(value = "classpath:data-integration.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CommentControllerIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private HttpHeaders adminHeaders;
    private String token;
    private Long idPost;
    private Long idComment;
    private Long finalIdPost;
    private Long outIdPost;
    private Long zeroIdPost;
    private Long notHasIdComment;
    private Long finalIdComment;
    private CommentDto updateCommentDto;
    private CommentDto emptyUpdateCommentDto;

    @BeforeEach
    void setUp(){
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        idPost=1L;
        idComment=58L;
        finalIdPost=100L;
        outIdPost=101L;
        zeroIdPost=0L;
        notHasIdComment=200L;
        finalIdComment=13L;
        updateCommentDto= new CommentDto();
        emptyUpdateCommentDto= new CommentDto();
        updateCommentDto.setName("Nuevo nombre");
        updateCommentDto.setEmail("emailNuevo@gmail.com");
        updateCommentDto.setBody("He conseguido editar el comentario");
        User admin = new User(1L,"Micah Eakle","meakle0","meakle0@newsvine.com", "kJ3(1SY6uMM", Set.of(new Role((short)1,"ADMIN")));
        token=jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken(
                admin.getUsername(),admin.getRoles()));
        adminHeaders=new HttpHeaders();
        adminHeaders.setContentType(MediaType.APPLICATION_JSON);
        adminHeaders.setBearerAuth(token);
    }

    @Test
    void createComment() {
    }

    @Test
    void getCommentsByPostId() {
    }

    @Test
    void getCommentById() {
    }

    //Sebastián Millán
    @Test
    void whenIdPostIdCommentAndUpdateCommentDtoIsOk_theReturnHttp200() {
        ResponseEntity<CommentDto> response = testRestTemplate.exchange("http://localhost:"+port+"/api/v1/posts/"+idPost+"/comments/"+idComment,
                HttpMethod.PUT,new HttpEntity<>(updateCommentDto,adminHeaders), CommentDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(idComment,response.getBody().getId());
        assertEquals(updateCommentDto.getName(), response.getBody().getName());
    }
    //Sebastián Millán
    @Test
    void whenIdPostIsOkButIdCommentNotExists_theReturnHttp404() {
        ResponseEntity<CommentDto> response = testRestTemplate.exchange("http://localhost:"+port+"/api/v1/posts/"+idPost+"/comments/"+notHasIdComment,
                HttpMethod.PUT,new HttpEntity<>(updateCommentDto,adminHeaders), CommentDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    //Sebastián Millán
    @Test
    void whenIdPostIdIsZero_theReturnHttp404() {
        ResponseEntity<CommentDto> response = testRestTemplate.exchange("http://localhost:"+port+"/api/v1/posts/"+zeroIdPost+"/comments/"+idComment,
                HttpMethod.PUT,new HttpEntity<>(updateCommentDto,adminHeaders), CommentDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    //Sebastián Millán
    @Test
    void whenIdPostIsOut_theReturnHttp404() {
        ResponseEntity<CommentDto> response = testRestTemplate.exchange("http://localhost:"+port+"/api/v1/posts/"+outIdPost+"/comments/"+idComment,
                HttpMethod.PUT,new HttpEntity<>(updateCommentDto,adminHeaders), CommentDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    //Sebastián Millán
    @Test
    void whenIdPostAndIdCommentIsFinal_theReturnHttp200() {
        ResponseEntity<CommentDto> response = testRestTemplate.exchange("http://localhost:"+port+"/api/v1/posts/"+finalIdPost+"/comments/"+finalIdComment,
                HttpMethod.PUT,new HttpEntity<>(updateCommentDto,adminHeaders), CommentDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(finalIdComment,response.getBody().getId());
        assertEquals(updateCommentDto.getName(), response.getBody().getName());
    }
    //Sebastián Millán
    @Test
    void whenUpdateCommentDtoIsNotValid_theReturnHttp400() {
        ResponseEntity<CommentDto> response = testRestTemplate.exchange("http://localhost:"+port+"/api/v1/posts/"+finalIdPost+"/comments/"+finalIdComment,
                HttpMethod.PUT,new HttpEntity<>(emptyUpdateCommentDto,adminHeaders), CommentDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteComment() {
    }
}