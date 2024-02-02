package com.springboot.blog.controller;

import com.springboot.blog.entity.Category;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryControllerTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void addCategoryWithOKStatus() {
        Category category1 = Category.builder()
                .name("Category 1")
                .description("Description 1")
                .posts(new ArrayList<>())
                .build();

    }

    @Test
    void getCategory() {
    }

    @Test
    void getCategories() {
    }

    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategory() {
    }
}