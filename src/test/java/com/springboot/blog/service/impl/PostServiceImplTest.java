package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceImplTest {

    @BeforeAll
    void init (){

        Post p = new Post(
                1,
                "Titulo 1",

        );

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

    @Test
    void deletePostById() {
    }

    @Test
    void getPostsByCategory() {
    }
}