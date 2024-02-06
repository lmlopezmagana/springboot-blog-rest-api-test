package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import com.springboot.blog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @InjectMocks
    private PostController categoryController;
    @Mock
    private PostService postService;

    @Mock
    CategoryRepository categoryRepository;

    private PostDto postDto;

    @BeforeEach
    public void setUp(){
        CommentDto comment = new CommentDto();
        Category category = new Category();
        category.setId(1L);
        category.setName("nombre");
        category.setDescription("descripcion de la categoria");

        when(categoryRepository.save(category)).thenReturn(category);

        postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("titulo");
        postDto.setDescription("descripcion del post");
        postDto.setContent("Este es el contenido");
        postDto.setComments(Set.of(comment));
        postDto.setCategoryId(category.getId());

        when(postService.getPostById(postDto.getId())).thenReturn(postDto);
    }

    @Test
    void createPost_expectedResponse400() throws Exception{
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createPost_expectedResponse201() throws Exception{
        mockMvc.perform(post("/api/posts")
                                .content(objectMapper.writeValueAsString(postDto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllPosts() {
    }

    @Test
    void getPostById() {
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