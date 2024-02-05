package com.springboot.blog.Category;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    @WithMockUser(username = "Alvaro", roles = {"ADMIN"})
    void addCategory() throws Exception {
        CategoryDto dto = new CategoryDto(1L,"Category name","Description category");
        when(service.addCategory(any(CategoryDto.class))).thenReturn(dto);

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("Category name")))
                .andExpect(jsonPath("$.description",is("Description category")));

        MvcResult result = mockMvc.perform(post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertEquals(response, objectMapper.writeValueAsString(dto));

    }

    @Test
    void getCategory() throws Exception {
        CategoryDto dto = new CategoryDto(1L,"Category name","Description category");
        when(service.getCategory(dto.getId())).thenReturn(dto);

        mockMvc.perform(get("/api/v1/categories/{id}",dto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is(dto.getName())));
    }

    @Test
    void getCategories() throws Exception {
        CategoryDto dto = new CategoryDto(1L,"Category name","Description category");
        CategoryDto dto2 = new CategoryDto(2L,"Name 2","Description 2");
        List<CategoryDto> data = List.of(dto,dto2);

        when(service.getAllCategories()).thenReturn(data);

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].id",is(2)))
                .andExpect(jsonPath("$[1].name",is(dto2.getName())));

    }

    @Test
    @WithMockUser(username = "Alvaro", roles = {"ADMIN"})
    void updateCategory() throws Exception {
        CategoryDto dto = new CategoryDto(1L,"Category name","Description category");
        CategoryDto dtoUpdated = new CategoryDto(1L,"Category test UPDATED","Description UPDATED");

        when(service.updateCategory(any(CategoryDto.class),anyLong())).thenReturn(dtoUpdated);

        mockMvc.perform(put("/api/v1/categories/{id}",dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoUpdated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Category test UPDATED")))
                .andExpect(jsonPath("$.description",is("Description UPDATED")));

        MvcResult result = mockMvc.perform(put("/api/v1/categories/{id}",dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertEquals(response, objectMapper.writeValueAsString(dtoUpdated));
    }

    @Test
    @WithMockUser(username = "Alvaro", roles = {"ADMIN"})
    void deleteCategory() throws Exception {
        CategoryDto dto = new CategoryDto(1L,"Category name","Description category");
        mockMvc.perform(delete("/api/v1/categories/{id}",dto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Category deleted successfully!."));

        verify(service, times(1)).deleteCategory(dto.getId());
    }
}