package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PostServiceImplTest {

    @Mock
    PostRepository postRepository;

   @Mock
    CategoryRepository categoryRepository;

   @InjectMocks
   PostServiceImpl postService;

    @BeforeAll
    void init (){

        CommentDto commentDto = new CommentDto();

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);

        PostDto postDto = new PostDto();

        postDto.setId(1L);
        postDto.setTitle("titulo 1");
        postDto.setDescription("eferf");
        postDto.setContent("wefewf");
        postDto.setComments(Set.of(commentDto));
        postDto.setId(1L);
    }

    Category c = new Category();

    @Test
    void createPost() {

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(c));
        

    }

    @Test
    void getAllPosts() {
    }

    @Test
    void getPostById() {
    }

    @Test
    void updatePost() {
    }

    @Test
    void deletePostById() {
    }

    @Test
    void getPostsByCategory() {
    }
}