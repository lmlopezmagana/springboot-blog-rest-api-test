package com.springboot.blog.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

    @InjectMocks
    CategoryController categoryController;

    @Autowired
    private MockMvc mockMvc;

    //Sebastián Millán
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenNewCategoryIsValid_thenReturnHttp201() throws Exception{

    }
    //Sebastián Millán
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenNewCategoryIsInValid_thenReturnHttp401() throws Exception{

    }
    //Sebastián Millán
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenUserHasAdminRole_thenReturnHttp401() throws Exception{

    }
    //Sebastián Millán
    @Test
    @WithMockUser()
    void whenUserHasUserRole_thenReturnHttp401() throws Exception{

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