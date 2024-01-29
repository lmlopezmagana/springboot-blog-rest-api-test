package com.springboot.blog.comments;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @InjectMocks
    CommentServiceImpl commentService;

    @Mock
    CommentRepository commentRepository;

    @Mock
    PostRepository postRepository;

    static ModelMapper mapper = new ModelMapper();

    @BeforeAll
    static void init() {
        c.setId(1L);
        c.setName("Comment name");
        c.setBody("Comment body");
        c.setEmail("email@email.com");

        comment = mapper.map(c, Comment.class);
    }

    Post p = Mockito.mock(Post.class);
    static Comment comment = new Comment();
    static CommentDto c = new CommentDto();

    @Test
    void createComment() {
        when(postRepository.findById(p.getId())).thenReturn(Optional.ofNullable(p));
        when(commentRepository.save(comment)).thenReturn(comment);
        when(p.getId()).thenReturn(1L);

        CommentDto result = commentService.createComment(p.getId(),c);

        assertEquals("Comment name", result.getName());
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