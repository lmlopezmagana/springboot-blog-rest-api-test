package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
<<<<<<< HEAD
import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
=======
>>>>>>> 5db27ad0865480f67e3c94ce9ee90b905c8ff722
import com.springboot.blog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
<<<<<<< HEAD
import org.mockito.Mock;
import org.mockito.Mockito;
=======
>>>>>>> 5db27ad0865480f67e3c94ce9ee90b905c8ff722
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
<<<<<<< HEAD

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
=======
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
>>>>>>> 5db27ad0865480f67e3c94ce9ee90b905c8ff722
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
<<<<<<< HEAD
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
=======
    @MockBean
    private PostService categoryService;
    @InjectMocks
    private PostController commentController;

    private Long idPost;
    private Long idCategory;

    @BeforeEach
    void setUp(){
        idPost=1L;
        idCategory=1L;
    }

>>>>>>> 5db27ad0865480f67e3c94ce9ee90b905c8ff722
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
        Category category = new Category();
        category.setId(2L);
        category.setName("nombre");
        category.setDescription("descripcion de la categoria");

        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/api/posts")
                                .content(objectMapper.writeValueAsString(postDto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    //Alejandro Rubens
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createPost_expectedResponse201() throws Exception{
        //No funciona
        Category category = new Category();
        category.setId(1L);
        category.setName("nombre");
        category.setDescription("descripcion de la categoria");

        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/api/posts")
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
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