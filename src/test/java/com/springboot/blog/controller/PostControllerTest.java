package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostService postService;
    @InjectMocks
    private PostController commentController;

    private Long idPost;
    private Long idCategory;

    private PostDto postDto;

    @BeforeEach
    void setUp(){
        idPost=1L;
        idCategory=1L;
        postDto = new PostDto();
        postDto.setId(1l);
        postDto.setTitle("Title");
        postDto.setDescription("Description");
        postDto.setContent("Content");
    }

    @Test
    void createPost() {
    }

    //Marco Pertegal
    @Test
    void whenPostsFoundThenReturnHttp200() throws Exception{
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "title";
        String sortDir = "ASC";
        PostDto postDto1 = new PostDto();
        postDto1.setId(2l);
        postDto1.setTitle("Title1");
        postDto1.setDescription("Description1");
        postDto1.setContent("Content1");
        List<PostDto> postDtoList = List.of(postDto, postDto1);

        PostResponse postResponse = new PostResponse(postDtoList, 0 , 10, 2, 1, true);
        Mockito.when(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir)).thenReturn(postResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    //Marco Pertegal
    @Test
    void whenPostFoundThenReturnHttp200() throws Exception{
        Mockito.when(postService.getPostById(idPost)).thenReturn(postDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{id}", idPost)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(postDto.getTitle())));
        Mockito.verify(postService, times(1)).getPostById(idPost);
    }
    //Marco Pertegal
    @Test
    void whenPostIdNotFoundThenReturn404() throws Exception{
        Mockito.doThrow(new ResourceNotFoundException("Post", "id", idPost))
                .when(postService).getPostById(idPost);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{id}", idPost)
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updatePost() {
    }

    //Sebastián Millán
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenPostIdExistAndAdminRole_thenReturnHttp200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/{id}",idPost))
                .andExpect(content().string("Post entity deleted successfully."))
                .andExpect(status().isOk());
        verify(postService, times(1)).deletePostById(idPost);
    }

    //Sebastián Millán
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenPostIdIsWrongAndAdminRole_thenReturnHttp400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/{id}","null"))
                .andExpect(status().isBadRequest());
        verify(postService, never()).deletePostById(idPost);
    }

    //Sebastián Millán
    @Test
    @WithMockUser(authorities = {"USER"})
    void whenPostIdExistsAndUserRole_thenReturnHttp401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/{id}",idPost))
                .andExpect(status().isUnauthorized());
        verify(postService, never()).deletePostById(idPost);

    }

    //Sebastián Millán
    @Test
    void whenPostIdExistAndNotAuth_thenReturnHttp401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/{id}",idPost)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
        verify(postService, never()).deletePostById(idPost);

    }

    //Sebastián Millán
    @Test
    void whenCategoryIdExists_thenReturnHttp200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/category/{id}",idCategory)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(postService, times(1)).getPostsByCategory(idCategory);
    }
    //Sebastián Millán
    @Test
    void whenCategoryIdIsWrong_thenReturnHttp400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/category/{id}","null")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(postService, never()).getPostsByCategory(idCategory);
    }

}