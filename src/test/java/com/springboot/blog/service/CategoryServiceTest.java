package com.springboot.blog.service;

import com.springboot.blog.config.ModelMapperConfig;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void addCategory() {
        var id = 1L;
        var name = "testName";
        var description = "testDescription";
        CategoryDto categoryDtoExpected
                = new CategoryDto( id, name, description);
        Category categoryExpected = new Category(id, name, description, null);

        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(categoryExpected);


        CategoryDto categoryDto  = categoryService.addCategory(categoryDtoExpected);

        assertEquals(categoryDto.getId(), categoryDtoExpected.getId());
        assertEquals(categoryDto.getName(), categoryDtoExpected.getName());
        assertEquals(categoryDto.getDescription(), categoryDtoExpected.getDescription());

        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.of(categoryExpected));
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        assertTrue(categoryOptional.isPresent());
        assertEquals(categoryOptional.get().getName(), name);
        assertEquals(categoryOptional.get().getDescription(), description);

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