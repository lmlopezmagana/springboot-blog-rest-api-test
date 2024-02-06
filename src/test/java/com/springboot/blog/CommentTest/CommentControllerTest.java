package com.springboot.blog.CommentTest;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private CommentService commentService;

    /*@Test
    public void testCreateComment() throws Exception {
        long postId = 1L;
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setBody("Test comment");

        when(commentService.createComment(eq(postId), any(CommentDto.class))).thenReturn(commentDto);

        mockMvc.perform(post("/api/v1/posts/" + postId + "/comments")
                        .with(user("user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Name\", \"email\":\"test@email.com\", \"body\":\"Test comment\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.body").value("Test comment"));


        verify(commentService, times(1)).createComment(eq(postId), any(CommentDto.class));
    }*/

    @Test
    @WithMockUser(username = "Javi", roles = {"ADMIN"})
    void testCreateComment() throws  Exception{

        Comment comment = new Comment(1L,"hola","angel@gmail","bodyyyyyyyyyyyyy",new Post());
        long postId = 1L;
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        when(commentService.createComment(any(Long.class),any(CommentDto.class))).thenReturn(commentDto);
        mockMvc.perform(post("/api/v1/posts/"+postId+"/comments")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(commentDto)).accept(APPLICATION_JSON)).andExpect(status().isCreated());

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/posts/"+postId+"/comments")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(commentDto)).accept(APPLICATION_JSON)).andReturn();

        String resultado = mvcResult.getResponse().getContentAsString();
        assertThat(resultado).isEqualToIgnoringCase(objectMapper.writeValueAsString(commentDto));
    }


}
