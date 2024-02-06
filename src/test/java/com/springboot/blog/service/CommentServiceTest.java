package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    static CommentRepository commentRepository;
    @Mock
    static PostRepository postRepository;
    static ModelMapper modelMapper = new ModelMapper();
    static Post p = Mockito.mock(Post.class);
    static Comment comment = new Comment();
    static CommentDto c = new CommentDto();

    @InjectMocks
    static CommentService commentService = new CommentServiceImpl(commentRepository, postRepository, modelMapper);

    @BeforeAll
    static void init() {
        c.setId(2L);
        c.setName("Comment name");
        c.setBody("Comment body");
        c.setEmail("email@email.com");

        comment = modelMapper.map(c, Comment.class);

        comment.setPost(p);
    }

    @Test
    void createComment() {
        long postId = 1L;
        long commentId = 1L;
        Post post = new Post();
        post.setId(1L);
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setPost(post);
        CommentDto commentDto = new CommentDto();
        commentDto.setId(commentId);
        commentDto.setName("Paco");
        commentDto.setEmail("paco@gmail.com");
        commentDto.setBody("Lorem ipsum dolor sit amet");

        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        Mockito.when(modelMapper.map(commentDto, Comment.class)).thenReturn(comment);
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(comment);

        CommentDto result = commentService.createComment(post.getId(), commentDto);

        Mockito.verify(postRepository).findById(commentDto.getId());
        Mockito.verify(commentRepository).save(any(Comment.class));

        assertEquals(commentDto.getName(), result.getName());
        assertEquals(commentDto.getEmail(), result.getEmail());
        assertEquals(commentDto.getBody(), result.getBody());
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
        System.out.println(post);
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
        System.out.println(updatedComment);
        assertEquals(commentRequest.getName(), updatedComment.getName());
        assertEquals(commentRequest.getEmail(), updatedComment.getEmail());
        assertEquals(commentRequest.getBody(), updatedComment.getBody());

    }

    @Test
    void deleteComment() {
    }
}