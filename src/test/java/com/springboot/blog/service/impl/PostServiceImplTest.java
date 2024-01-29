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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    PostRepository postRepository;

   @Mock
    CategoryRepository categoryRepository;

   @InjectMocks
   PostServiceImpl postService;

   @Mock
    ModelMapper mapper;

    @BeforeAll
    static void init (){

        CommentDto commentDto = new CommentDto();

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);

        postDto.setId(1L);
        postDto.setTitle("titulo 1");
        postDto.setDescription("eferf");
        postDto.setContent("wefewf");
        postDto.setComments(Set.of(commentDto));
        postDto.setId(1L);
    }

    static Category category = new Category();
    static PostDto postDto = new PostDto();

    @Test
    void createPost() {

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        when(postRepository.save(any(Post.class))).thenReturn(new Post());

        PostDto result = postService.createPost(postDto);

        assertEquals("titulo 1", result.getTitle());

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