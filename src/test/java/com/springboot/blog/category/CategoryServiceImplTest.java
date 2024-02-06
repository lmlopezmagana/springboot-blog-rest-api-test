package com.springboot.blog.category;

import com.springboot.blog.entity.Category;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import com.springboot.blog.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    static CategoryRepository categoryRepository;

    static ModelMapper modelMapper= new ModelMapper();

    @InjectMocks
    static CategoryService categoryService= new CategoryServiceImpl(categoryRepository, modelMapper);

    static Category category= new Category();

    static CategoryDto c = new CategoryDto();

    @BeforeAll
    static void init(){
        c.setId(1L);
        c.setName("Category test");
        c.setDescription("Category test description");

        category= modelMapper.map(c, Category.class);
    }

    @Test
    void addCategoryTest() {
        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        CategoryDto result= categoryService.addCategory(c);

        assertEquals("Category test description", result.getDescription());
        verify(categoryRepository, times(1)).save(Mockito.any(Category.class));

    }

//    @Test
//    void getCategoryByIdTest() {
//        when(categoryRepository.findById(category.getId())).thenReturn(c);
//    }

    @Test
    void getAllCategoriesTest() {
    }

    @Test
    void updateCategoryTest() {
    }

    @Test
    void deleteCategoryTest() {
    }

}