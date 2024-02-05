package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.impl.CommentServiceImpl;
import org.jetbrains.annotations.NotNull;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
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
    void createComment() {
    }

    @Test
    @WithMockUser(username = "username", roles = {"USER","ADMIN"})
    void getCommentsByPostId() throws Exception {

        Long id = 1L;

        List<CommentDto> list = getCommentDtos();

        Mockito.when(commentService.getCommentsByPostId(eq(id))).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/{postId}/comments",id)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Manolo")))
                .andExpect(jsonPath("$[0].email", is("manolo@gmail.com")))
                .andExpect(jsonPath("$[0].body", is("texto texto y muhco texto")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Manolq")))
                .andExpect(jsonPath("$[1].email", is("manola@gmail.com")))
                .andExpect(jsonPath("$[1].body", is("texto texto y muhco texto")));



    }

    @NotNull
    private static List<CommentDto> getCommentDtos() {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setName("Manolo");
        commentDto.setEmail("manolo@gmail.com");
        commentDto.setBody("texto texto y muhco texto");

        CommentDto commentDto2 = new CommentDto();
        commentDto2.setId(2L);
        commentDto2.setName("Manolq");
        commentDto2.setEmail("manola@gmail.com");
        commentDto2.setBody("texto texto y muhco texto");

        List<CommentDto> list = new ArrayList<>(List.of(commentDto,commentDto2));
        return list;
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