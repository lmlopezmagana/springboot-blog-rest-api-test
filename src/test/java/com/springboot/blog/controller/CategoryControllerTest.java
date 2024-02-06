package com.springboot.blog.controller;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CategoryService categoryService;
    @InjectMocks
    private CategoryController categoryController;
    @Mock
    private CategoryRepository repository;

    private CategoryDto categoryDto;
    private CategoryDto categoryDto2;

    @BeforeEach
    public void setUp(){
        categoryDto= new CategoryDto(1L,"Análisis","Análisis profundo de la politica nacional");
        categoryDto2 = new CategoryDto(2L,"categoria2","descripcion");
        when(categoryService.getCategory(categoryDto2.getId())).thenReturn(categoryDto2);
    }

    @Test
    void addCategory() {
    }

    //Alejandro Rubens
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