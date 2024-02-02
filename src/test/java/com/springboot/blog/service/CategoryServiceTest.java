package com.springboot.blog.service;

import com.springboot.blog.entity.Category;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;


    @Test
    void addCategory() {
    }

    @Test
    void getCategory() {
    }

    @Test
    void getAllCategories_ReturnsAllCategories() {
        List<Category> categories = List.of(new Category(), new Category());
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDto> dtoList = List.of(new CategoryDto(), new CategoryDto());
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(CategoryDto.class)))
                .thenReturn(dtoList.get(0))
                .thenReturn(dtoList.get(1));

        List<CategoryDto> result = categoryService.getAllCategories();

        assertEquals(dtoList, result);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategory() {
    }
}