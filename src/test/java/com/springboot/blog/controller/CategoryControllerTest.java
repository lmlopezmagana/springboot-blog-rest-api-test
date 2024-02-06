package com.springboot.blog.controller;

<<<<<<< HEAD
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
=======
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.payload.CategoryDto;
>>>>>>> 5db27ad0865480f67e3c94ce9ee90b905c8ff722
import com.springboot.blog.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
<<<<<<< HEAD
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
>>>>>>> 5db27ad0865480f67e3c94ce9ee90b905c8ff722
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CategoryService categoryService;
    @InjectMocks
<<<<<<< HEAD
    private CategoryController categoryController;
    @Mock
    private CategoryRepository repository;

    private CategoryDto categoryDto;
    private CategoryDto categoryDto2;
=======
    private CategoryController commentController;

    private CategoryDto categoryDto;
>>>>>>> 5db27ad0865480f67e3c94ce9ee90b905c8ff722

    @BeforeEach
    public void setUp(){
        categoryDto= new CategoryDto(1L,"Análisis","Análisis profundo de la politica nacional");
<<<<<<< HEAD
        categoryDto2 = new CategoryDto(2L,"categoria2","descripcion");
        when(categoryService.getCategory(categoryDto2.getId())).thenReturn(categoryDto2);
    }

    @Test
    void addCategory() {
    }

    //Alejandro Rubens
=======
    }

    //Sebastián Millán
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenNewCategoryIsValidAndAdminRole_thenReturnHttp201() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    //Sebastián Millán
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenNewCategoryIsInvalidAndAdminRole_thenReturnHttp400() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(categoryService, never()).addCategory(categoryDto);

    }

    @Test
    @WithMockUser()
    void whenNewCategoryIsValidAndUserRole_thenReturnHttp401() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
        verify(categoryService, never()).addCategory(categoryDto);

    }


    //Sebastián Millán
    @Test
    void whenNotAuth_thenReturnHttp401() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
        verify(categoryService, never()).addCategory(categoryDto);

    }


>>>>>>> 5db27ad0865480f67e3c94ce9ee90b905c8ff722
    @Test
    void getCategory_expectedResponse200() throws Exception {
        mockMvc.perform(get("/api/v1/categories/{id}",categoryDto2.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(2)))
                .andExpect(jsonPath("$.name",is(categoryDto2.getName())))
                .andExpect(jsonPath("$.description",is(categoryDto2.getDescription())));
    }

    //Alejandro Rubens
    @Test
    void getCategory_expectedResponse200EmptyString() throws Exception {
        mockMvc.perform(get("/api/v1/categories/{id}", 23L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    //Alejandro Rubens
    @Test
    void getCategory_expectedResponse400() throws Exception {
        mockMvc.perform(get("/api/v1/categories/{id}", "s")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCategories() {
    }

    //Alejandro Rubens
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void updateCategory_expectedResponse200() throws Exception {
        mockMvc.perform(get("/api/v1/categories/{id}", 1L)
                        .content(objectMapper.writeValueAsString(categoryDto2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    //Alejandro Rubens
    @Test
    void updateCategory_expectedResponse400() throws Exception {
        mockMvc.perform(get("/api/v1/categories/{id}", "s")
                        .content(objectMapper.writeValueAsString(categoryDto2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteCategory() {
    }
}