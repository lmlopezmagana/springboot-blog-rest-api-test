package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.impl.CommentServiceImpl;
import io.swagger.v3.oas.annotations.links.Link;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CommentController.class)
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CommentService commentService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @InjectMocks
    private CommentController commentController;

    private Long postId;
    private CommentDto commentDto;

    @BeforeEach
    void setUp(){
        postId=1L;
        commentDto= new CommentDto();
        commentDto.setId(1L);
        commentDto.setBody("Me ha encantado la reseña");
        commentDto.setEmail("sebas@gmail.com");
        commentDto.setName("Sebastián");

    }

    //Sebastián Millán
    @Test
    @WithMockUser
    void whenPostIdExistsAndNewCommentIsValidAndAuth_thenReturnHttp201() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts/{postId}/comments",postId).
                        content(objectMapper.writeValueAsString(commentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                        .andExpect(status().isCreated());
    }
    //Sebastián Millán
    @Test
    @WithMockUser
    void whenPostIdExistsAndNewCommentIsInvalidAndAuth_thenReturnHttp400() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts/{postId}/comments",postId).
                        content(objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    //Sebastián Millán
    @Test
    @WithMockUser
    void whenPostIdIsWrongAndNewCommentIsValidAndAuth_thenReturnHttp400() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts/{postId}/comments","a")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }
    //Sebastián Millán
    @Test
    void whenPostIdExistsAndWithoutAuth_thenReturnHttp403() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts/{postId}/comments",postId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
    //Sebastián Millán
    @Test
    @WithMockUser
    void whenPostIdExistsAndAuth_thenReturnHttp200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/{postId}/comments",postId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    //Sebastián Millán
    @Test
    void whenPostIdExistsAndWithoutAuth_thenReturnHttp401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/{postId}/comments",postId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
    //Sebastián Millán
    @Test
    @WithMockUser
    void whenPostIdIsWrongAndAuth_thenReturnHttp401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/{postId}/comments","a")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCommentById() {
    }

    @Test
    void updateComment() {
    }

    @Test
    void deleteComment() {
    }
}