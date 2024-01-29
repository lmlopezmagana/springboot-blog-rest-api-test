package com.springboot.blog.comments;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    static CommentRepository commentRepository;
    @Mock
    static PostRepository postRepository;
    static ModelMapper mapper = new ModelMapper();
    Post p = Mockito.mock(Post.class);
    static Comment comment = new Comment();
    static CommentDto c = new CommentDto();

    @InjectMocks
    static CommentService commentService = new CommentServiceImpl(commentRepository, postRepository, mapper);

    @BeforeAll
    static void init() {
        c.setId(1L);
        c.setName("Comment name");
        c.setBody("Comment body");
        c.setEmail("email@email.com");

        comment = mapper.map(c, Comment.class);
    }
    @Test
    void createComment() {
        when(p.getId()).thenReturn(1L);
        when(postRepository.findById(p.getId())).thenReturn(Optional.ofNullable(p));
        when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(comment);

        CommentDto result = commentService.createComment(p.getId(),c);

        assertEquals("Comment name", result.getName());
        verify(commentRepository, times(1)).save(Mockito.any(Comment.class));
        verify(postRepository).findById(eq(1L));
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