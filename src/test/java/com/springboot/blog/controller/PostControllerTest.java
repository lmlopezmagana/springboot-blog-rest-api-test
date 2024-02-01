package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.PostService;
import com.springboot.blog.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PostControllerTest {

    @InjectMocks
    PostController postController;

    @Mock
    PostServiceImpl postService;

    private Long idPost;
    private Long idCategory;
    private List<PostDto> expectedResultListPostDto;

    @BeforeEach
    void setUp(){
        idPost=1L;
        idCategory=1L;
        expectedResultListPostDto= new ArrayList<>();
        expectedResultListPostDto.add(new PostDto());
        expectedResultListPostDto.add(new PostDto());
    }

    @Test
    void createPost() {
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

    //Sebastián Millán
    @Test
    void whenPostIdExist_thenDeleteThePostAndReturn200() {
        doNothing().when(postService).deletePostById(Mockito.any(Long.class));
        ResponseEntity<String> response = postController.deletePost(idPost);

        verify(postService, times(1)).deletePostById(idPost);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Post entity deleted successfully.", response.getBody());
    }

    //Sebastián Millán
    @Test
    void whenCategoryIdExists_thenGetAllPostAndReturn200() {
        when(postService.getPostsByCategory(idCategory)).thenReturn(expectedResultListPostDto);
        ResponseEntity<List<PostDto>> response = postController.getPostsByCategory(idCategory);
        verify(postService, times(1)).getPostsByCategory(idCategory);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResultListPostDto, response.getBody());

    }
}