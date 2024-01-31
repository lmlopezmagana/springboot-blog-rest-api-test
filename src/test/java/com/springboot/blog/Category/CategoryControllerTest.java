package com.springboot.blog.Category;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryServiceImpl service;

    @Autowired
    ObjectMapper objectMapper;

    CategoryDto dto;

    @BeforeEach
    void setUp() {
        dto = new CategoryDto();
        dto.setId(1L);
        dto.setName("Category test");
        dto.setDescription("Category description test");
    }

    @Test
    @WithMockUser(username = "Alvaro", roles = {"USER","ADMIN"})
    void addCategory() throws Exception {
        when(service.addCategory(dto)).thenReturn(dto);

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

    }

    @Test
    void getCategory() throws Exception {
        when(service.getCategory(dto.getId())).thenReturn(dto);

        mockMvc.perform(get("/api/v1/categories/{id}",dto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is(dto.getName())));
    }

    @Test
    void getCategories() throws Exception {
        CategoryDto dto2 = new CategoryDto(2L,"Name 2","Description 2");
        List<CategoryDto> data = List.of(dto,dto2);

        when(service.getAllCategories()).thenReturn(data);

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].id",is(2)))
                .andExpect(jsonPath("$[1].name",is(dto2.getName())));

    }

    @Test
    @WithMockUser(username = "Alvaro", roles = {"USER","ADMIN"})
    void updateCategory() throws Exception {
        CategoryDto dtoUpdated = new CategoryDto(1L,"Category test UPDATED","Description UPDATED");

        when(service.updateCategory(dto,dto.getId())).thenReturn(dtoUpdated);

        mockMvc.perform(put("/api/v1/categories/{id}",dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoUpdated)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Alvaro", roles = {"USER","ADMIN"})
    void deleteCategory() throws Exception {

        mockMvc.perform(delete("/api/v1/categories/{id}",dto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Category deleted successfully!."));

        verify(service, times(1)).deleteCategory(dto.getId());
    }
}