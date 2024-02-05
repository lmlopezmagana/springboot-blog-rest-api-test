package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ModelMapper modelMapper;

    @Test
    void addCategory() {
    }

    @Test
    void getCategory() {
    }

    @Test
    void getAllCategories() {
    }

    //Sebastián Millán
    @Test
    void whenCategoryDtoAndCategoryIdIsPresent_thenUpdateCategoryAndTransformToDto() {
        Long categoryId = 2L;
        CategoryDto categoryDto = new CategoryDto(1L,"Análisis","Análisis profundo de la politica nacional");
        Category category = new Category(categoryId, "Partituras","Partituras de la banda de música", List.of());
        CategoryDto expectedDtoResult = new CategoryDto(categoryId,"Análisis","Análisis profundo de la politica nacional");


        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(modelMapper.map(category, CategoryDto.class)).thenReturn(expectedDtoResult);

        CategoryDto result = categoryService.updateCategory(categoryDto, categoryId);
        assertNotNull(result);
        assertEquals(categoryDto.getName(), result.getName());
        assertEquals(categoryDto.getDescription(), result.getDescription());
        assertEquals(categoryId, result.getId());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(Mockito.any());
        verifyNoMoreInteractions(categoryRepository);
    }

    //Sebastián Millán
    @Test
    void whenCategoryIdIsOther_thenThrowException() {
        Long categoryId = 5L;
        CategoryDto categoryDto = new CategoryDto(1L,"Análisis","Análisis profundo de la politica nacional");
        String expectedMessage= "Category not found with id : '"+categoryId+"'";

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class,()->{
            categoryService.updateCategory(categoryDto, categoryId);
        });

        assertEquals(exception.getMessage(), expectedMessage);

        verify(categoryRepository, times(1)).findById(categoryId);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void deleteCategory() {
    }
}