package com.springboot.blog.service.impl;

import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    CategoryRepository repository;

    @InjectMocks
    CategoryServiceImpl service;

    @Test
    void addCategory() {

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