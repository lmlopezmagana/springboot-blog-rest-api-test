package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.entity.Category;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerWOSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;


    @Test
    void addCategory() {
    }

    @Test
    void getCategory() throws Exception {

        Category category = new Category(1L,"Manolo","manoloCat",new ArrayList<>());


        CategoryDto categoryDto = new CategoryDto(1L,"Manolo","manoloCat");

        CategoryDto result = categoryService.getCategory(categoryDto.getId());

        Mockito.when(categoryService.getCategory(category.getId())).thenReturn(result);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name",is(categoryDto.getName())));
    }


    @Test
    void getCategories() throws Exception {
        CategoryDto categoryDto = new CategoryDto(1L, "Ropa", "Esto es una buena categoria");
        List <CategoryDto> categories = List.of(
                categoryDto
        );
        Mockito.when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(content().json(objectMapper.writeValueAsString(categories)));
    }

    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategory() {
    }
}