package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

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
    void updateComment_PostNotFound() {
        Long postId = 1L;
        long commentId = 2L;
        CommentDto commentRequest = new CommentDto();

        Mockito.when(postRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> commentService.updateComment(postId, commentId, commentRequest));
    }

    @Test
    void updateComment_CommentNotFound(){
        Post post = new Post();
        post.setId(1L);
        long commentId = 2L;
        CommentDto commentRequest = new CommentDto();

        Mockito.when(postRepository.findById(Mockito.any())).thenReturn(Optional.of(post));
        Mockito.when(commentRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commentService.updateComment(post.getId(), commentId, commentRequest));
    }

    @Test
    void updateComment_CommentPostIdDifferent(){
        Post post = new Post();
        post.setId(1L);
        Post post2 = new Post();
        post.setId(2L);
        Comment comment = new Comment();
        comment.setId(1);
        comment.setPost(post);
        CommentDto commentRequest = new CommentDto();

        Mockito.when(postRepository.findById(Mockito.any())).thenReturn(Optional.of(post2));
        Mockito.when(commentRepository.findById(Mockito.any())).thenReturn(Optional.of(comment));

        assertThrows(BlogAPIException.class, () -> commentService.updateComment(post2.getId(), comment.getId(), commentRequest));
    }

    @Test
    void updateComment_Successful(){
        Long postId = 1L;
        long commentId = 1L;
        Post post = new Post();
        post.setId(1L);
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setPost(post);
        CommentDto commentRequest = new CommentDto();
        commentRequest.setBody("Esto es un comentario");
        commentRequest.setName("Comentario 1");
        commentRequest.setEmail("alex@gmail.com");

        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        Mockito.when(commentRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(CommentDto.class))).thenReturn(commentRequest);

        CommentDto updatedComment = commentService.updateComment(postId, commentId, commentRequest);

        assertEquals(commentRequest.getName(), updatedComment.getName());
        assertEquals(commentRequest.getEmail(), updatedComment.getEmail());
        assertEquals(commentRequest.getBody(), updatedComment.getBody());

    }

    @Test
    void deleteComment() {
    }
}