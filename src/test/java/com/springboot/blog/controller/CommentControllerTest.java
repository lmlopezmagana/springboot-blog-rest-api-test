package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.impl.CommentServiceImpl;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = CommentController.class)
class CommentControllerTest {

    @InjectMocks
    CommentController commentController;

    @Mock
    CommentServiceImpl commentService;

    private Long postId;
    private CommentDto inputCommentDto;
    private CommentDto expectedNewCommentDto;

    @BeforeEach
    public void setUp(){
        postId=1L;
        when(commentService.createComment(postId, inputCommentDto)).thenReturn(expectedNewCommentDto);
        when(commentService.getCommentsByPostId(postId)).thenReturn(List.of(new CommentDto()));

    }

    //Sebastián Millán
    @Test
    void whenPostIdExistsAndNewCommentIsValid_thenReturnHttp201() throws Exception{
        ResponseEntity<CommentDto> response= commentController.createComment(postId, inputCommentDto);

        verify(commentService,times(1)).createComment(postId, inputCommentDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(response.getBody(), expectedNewCommentDto);
    }
    @Test
    void whenPostIdExists_thenReturnAllComments() {
        List<CommentDto> result = commentController.getCommentsByPostId(postId);
        assertEquals(result.size(), 1);
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