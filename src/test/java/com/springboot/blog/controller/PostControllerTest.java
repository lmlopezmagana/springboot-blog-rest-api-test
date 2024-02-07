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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @MockBean
    private PostService categoryService;
    @InjectMocks
    private PostController commentController;

    private Long idPost;
    private Long idCategory;

    @InjectMocks
    private PostController postController;
    @Mock
    private PostService postService;

    @Mock
    CategoryRepository categoryRepository;

    private PostDto postDto;

    @BeforeEach
    public void setUp(){
        idPost=1L;
        idCategory=1L;
        CommentDto comment = new CommentDto();

        postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("titulo");
        postDto.setDescription("descripcion del post");
        postDto.setContent("Este es el contenido");
        postDto.setComments(Set.of(comment));
        postDto.setCategoryId(1L);

        when(postService.getPostById(postDto.getId())).thenReturn(postDto);
    }
    //Alejandro Rubens

    @Test
    void createPost_expectedResponse401() throws Exception{
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
    //Alejandro Rubens
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createPost_expectedResponse400() throws Exception{
        //No funciona como se espera
        mockMvc.perform(post("/api/posts")
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    //Alejandro Rubens
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createPost_expectedResponse201() throws Exception{
        Category category = new Category();
        category.setId(1L);
        category.setName("nombre");
        category.setDescription("descripcion de la categoria");

        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

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
    @WithMockUser(roles = {"ADMIN"})
    void whenUpdatePostWithValidData_thenReturnHttp200() throws Exception {
        PostDto updatedPostDto = new PostDto();
        updatedPostDto.setId(1L);
        updatedPostDto.setTitle("Title");
        updatedPostDto.setDescription("Description");
        updatedPostDto.setContent("Content");

        long postId = 1L;
        when(categoryService.updatePost(updatedPostDto, postId)).thenReturn(updatedPostDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/{id}", postId)
                        .content(objectMapper.writeValueAsString(updatedPostDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedPostDto.getTitle()));
        verify(categoryService, times(1)).updatePost(updatedPostDto, postId);
    }
    @Test
    @WithMockUser(authorities = {"USER"})
    void whenUpdatePostWithValidData_thenReturnHttp401() throws Exception {
        // Arrange
        PostDto updatedPostDto = new PostDto();
        updatedPostDto.setId(1L);
        updatedPostDto.setTitle("Title");
        updatedPostDto.setDescription("Description");
        updatedPostDto.setContent("Content");

        long postId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/{id}", postId)
                        .content(objectMapper.writeValueAsString(updatedPostDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
        verify(categoryService, never()).updatePost(updatedPostDto, postId);
    }



    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenUpdatePostWithInvalidData_thenReturnHttp400() throws Exception {
        PostDto updatedPostDto = new PostDto();
        long postId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/{id}", postId)
                        .content(objectMapper.writeValueAsString(updatedPostDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(categoryService, never()).updatePost(updatedPostDto, postId);
    }



    //Sebastián Millán
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenPostIdExistAndAdminRole_thenReturnHttp200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/{id}",idPost))
                .andExpect(content().string("Post entity deleted successfully."))
                .andExpect(status().isOk());
        verify(categoryService, times(1)).deletePostById(idPost);
    }

    //Sebastián Millán
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenPostIdIsWrongAndAdminRole_thenReturnHttp400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/{id}","null"))
                .andExpect(status().isBadRequest());
        verify(categoryService, never()).deletePostById(idPost);
    }

    //Sebastián Millán
    @Test
    @WithMockUser(authorities = {"USER"})
    void whenPostIdExistsAndUserRole_thenReturnHttp401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/{id}",idPost))
                .andExpect(status().isUnauthorized());
        verify(categoryService, never()).deletePostById(idPost);

    }

    //Sebastián Millán
    @Test
    void whenPostIdExistAndNotAuth_thenReturnHttp401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/{id}",idPost)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
        verify(categoryService, never()).deletePostById(idPost);

    }

    //Sebastián Millán
    @Test
    void whenCategoryIdExists_thenReturnHttp200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/category/{id}",idCategory)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(categoryService, times(1)).getPostsByCategory(idCategory);
    }
    //Sebastián Millán
    @Test
    void whenCategoryIdIsWrong_thenReturnHttp400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/category/{id}","null")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(categoryService, never()).getPostsByCategory(idCategory);
    }

}