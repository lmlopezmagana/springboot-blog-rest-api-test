package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    PostRepository postRepository;

   @Mock
   CategoryRepository categoryRepository;

   ModelMapper mapper = new ModelMapper();

   @InjectMocks
   PostService postService = new PostServiceImpl(postRepository, mapper, categoryRepository);
//   static PostService postService = new PostServiceImpl(postRepository, mapper, categoryRepository);

    private Category category;
    private PostDto postDto;
    private Post post;

    @BeforeEach
    void init (){

        category = new Category();
        postDto = new PostDto();
        post = new Post();

        CommentDto commentDto = new CommentDto();

        category = new Category();
        category.setId(1L);
        category.setName("Example Category");


        postDto.setId(1L);
        postDto.setTitle("titulo 1");
        postDto.setDescription("eferf");
        postDto.setContent("wefewf");
        postDto.setCategoryId(category.getId());

        post = mapper.map(postDto, Post.class);
//        post.setCategory(category);
    }

    @Test
    void createPost() {

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(category));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDto result = postService.createPost(postDto);

        assertEquals("titulo 1", result.getTitle());

    }

    @Test
    void getAllPosts() {


    }

    @Test
    void getPostById() {

//        when(postRepository.findBy()).thenReturn()
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