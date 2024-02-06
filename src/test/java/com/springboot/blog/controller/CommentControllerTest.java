package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    private Post post;
    private Comment comment;
    private CommentDto commentDto;


    @BeforeEach
    public void setUp(){
        commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setName("nombre");
        commentDto.setEmail("email@gmail.com");
        commentDto.setBody("cuerpo del comentario");
        when(commentService.getCommentById(1L, 1L)).thenReturn(commentDto);
    }

    @Test
    void createComment() {
    }

    @Test
    void getCommentsByPostId() {
    }

    //Alejandro Rubens
    @Test
    void getCommentById_expectedResponse200() throws Exception{
        mockMvc.perform(get("/api/v1/posts/{postId}/comments/{id}",1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is(commentDto.getName())))
                .andExpect(jsonPath("$.email",is(commentDto.getEmail())))
                .andExpect(jsonPath("$.body",is(commentDto.getBody())));
    }
    //Alejandro Rubens
    @Test
    void getCommentById_expectedResponse200EmptyBody() throws Exception{
        mockMvc.perform(get("/api/v1/posts/{postId}/comments/{id}",1L, 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
    //Alejandro Rubens
    @Test
    void getCommentById_expectedResponse400() throws Exception{
        mockMvc.perform(get("/api/v1/posts/{postId}/comments/{id}",1L, "s")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateComment() {
    }

    //Alejandro Rubens
    @Test
    void deleteComment() throws Exception{
        mockMvc.perform(get("/api/v1/posts/{postId}/comments/{id}",1L, "s")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}