package com.springboot.blog.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CommentController.class)
class CommentControllerTest {

    @InjectMocks
    CommentController commentController;

    @Autowired
    MockMvc mockMvc;

    @Test
    void createComment() {
    }

    @Test
    void getCommentsByPostId() {
    }

    @Test
    void getCommentById() {
    }

    @Test
    void updateComment() {
    }

    @Test
    void deleteComment() {
    }
}