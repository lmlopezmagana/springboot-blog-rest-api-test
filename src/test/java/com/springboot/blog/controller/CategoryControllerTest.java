package com.springboot.blog.controller;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;
import com.springboot.blog.service.impl.CategoryServiceImpl;
import org.apache.coyote.Response;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {


    @Mock
    CategoryService categoryService;
    @InjectMocks
    CategoryController categoryController;

    private CategoryDto inputCateoryDto;
    private CategoryDto expectedSavedCategory;

    //Sebastián Millán
    @BeforeEach
    public void setUp(){
        inputCateoryDto= new CategoryDto(1L,"Análisis","Análisis profundo de la politica nacional");
        expectedSavedCategory= new CategoryDto(1L,"Análisis","Análisis profundo de la politica nacional");
        //categoryController = new CategoryController(categoryService);

        when(categoryService.addCategory(inputCateoryDto)).thenReturn(expectedSavedCategory);
    }

    //Sebastián Millán
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenNewCategoryIsValid_thenReturnHttp201() throws Exception{
        ResponseEntity<CategoryDto> response = categoryController.addCategory(inputCateoryDto);

        verify(categoryService, times(1)).addCategory(inputCateoryDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedSavedCategory, response.getBody());
    }
    //Sebastián Millán
    /*
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenNewCategoryIsInvalid_thenReturnHttp401() throws Exception{
        ResponseEntity<CategoryDto> response = categoryController.addCategory(null);

        verify(categoryService, never()).addCategory(any(CategoryDto.class));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
    */

    //Sebastián Millán
    /*
    @Test
    @WithMockUser(roles = {"USER"})
    void whenUserHasUserRole_thenReturnHttp401() throws Exception{
        ResponseEntity<CategoryDto> response = categoryController.addCategory(inputCateoryDto);

        verify(categoryService, never()).addCategory(inputCateoryDto);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNull(response.getBody());
    }
    */


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