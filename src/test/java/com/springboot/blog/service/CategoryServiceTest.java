package com.springboot.blog.service;

import com.springboot.blog.entity.Category;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.impl.CategoryServiceImpl;
import jakarta.inject.Inject;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void addCategory() {

        var id = 7L;
        var name = "testName";
        var description = "testDescription";

        Category categoryExpected = new Category(id, name, description, null);

        CategoryDto categoryDtoExpected
                = this.modelMapper.map(categoryExpected, CategoryDto.class);

        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(categoryExpected);

        CategoryDto categoryDto  = categoryServiceImpl.addCategory(categoryDtoExpected);

        assertEquals(categoryDto.getId(), categoryDtoExpected.getId());
        assertEquals(categoryDto.getName(), categoryDtoExpected.getName());
        assertEquals(categoryDto.getDescription(), categoryDtoExpected.getDescription());



    }

    @Test
    void getCategory() {
    }

    @Test
    void getAllCategories() {
    }

    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategory() {
    }
}