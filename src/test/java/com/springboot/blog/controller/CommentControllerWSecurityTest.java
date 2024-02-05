package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
class CommentControllerWSecurityTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @InjectMocks
    private CommentController commentController;
    @MockBean
    private CommentServiceImpl commentService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    @WithMockUser(username = "username",  roles = {"USER","ADMIN"})
    void createComment() throws Exception {

        long postId = 1L;
        long commentId = 1L;
        CommentDto commentDto = new CommentDto();
        commentDto.setId(commentId);
        commentDto.setName("Paco");
        commentDto.setEmail("paco@gmail.com");
        commentDto.setBody("Lorem ipsum dolor sit amet");

        Mockito.when(commentService.createComment(postId, commentDto)).thenReturn(commentDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts/{postId}/comments", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getCommentsByPostId() {
    }

    @Test
    void getCommentById() {
    }

    @Test
    @WithMockUser(username = "username",  roles = {"USER","ADMIN"})
    void updateComment() throws Exception {
        Long postId = 1L;
        long commentId = 1L;
        CommentDto updatedComment = new CommentDto();
        updatedComment.setId(commentId);
        updatedComment.setName("Comentario 1");
        updatedComment.setEmail("alex@gmail.com");
        updatedComment.setBody("Un comentario guapo");

        Mockito.when(commentService.updateComment(postId, commentId, updatedComment)).thenReturn(updatedComment);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/posts/{postId}/comments/{id}", postId, commentId)
                    .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedComment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedComment.getName())));
    }

    @Test
    void deleteComment() {
    }
}