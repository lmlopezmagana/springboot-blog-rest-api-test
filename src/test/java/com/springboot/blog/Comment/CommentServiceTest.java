package com.springboot.blog.Comment;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentServiceImpl commentService;
    private ModelMapper mapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        commentService.setMapper(mapper);
    }

    @Test
    void createCommentTest() {
        Category category = new Category(1L, "categoria", "descripcion", List.of());
        Post posts1 = new Post(1L, "titulo del post", "descripcion post", "contenido del post", Set.of(), category);
        Comment comments = new Comment(1L, "nombre del comentario", "email del comment", "cuerpo del comment", posts1);
        commentRepository.save(comments);
        CommentDto commentDto = new CommentDto();
        commentDto.setName("nombre del comentario");
        commentDto.setEmail("email del comment");
        commentDto.setBody("cuerpo del comment");


        comments = mapper.map(commentDto, Comment.class);
        comments.setPost(posts1);

        category.setPosts(List.of(posts1));
        posts1.setComments(Set.of(comments));

        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(posts1));
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(comments);

        CommentDto newCommentDto = commentService.createComment(1L, commentDto);

        assertEquals(1L, newCommentDto.getId());
    }

    @Test
    void getCommentsByPostIdTest() {
        Category category = new Category(1L, "categoria", "descripcion", List.of());
        Post posts1 = new Post(1L, "titulo del post", "descripcion post", "contenido del post", Set.of(), category);
        Comment comment1 = new Comment(1L, "Nombre1", "email1@example.com", "Cuerpo1", posts1);
        Comment comment2 = new Comment(2L, "Nombre2", "email2@example.com", "Cuerpo2", posts1);
        List<Comment> expectedComments = List.of(comment1, comment2);

        Mockito.when(commentRepository.findByPostId(1L)).thenReturn(expectedComments);


        List<CommentDto> actualComments = commentService.getCommentsByPostId(1L);


        assertEquals(expectedComments.size(), actualComments.size());


        for (int i = 0; i < expectedComments.size(); i++) {
            Comment expectedComment = expectedComments.get(i);
            CommentDto actualCommentDto = actualComments.get(i);

            assertEquals(expectedComment.getId(), actualCommentDto.getId());
            assertEquals(expectedComment.getName(), actualCommentDto.getName());
            assertEquals(expectedComment.getEmail(), actualCommentDto.getEmail());
            assertEquals(expectedComment.getBody(), actualCommentDto.getBody());
        }

        }

    @Test
    void getCommentByIdTest() {

        Category category = new Category(1L, "categoria", "descripcion", List.of());
        Post posts1 = new Post(1L, "titulo del post", "descripcion post", "contenido del post", Set.of(), category);
        Comment comment = new Comment(1L, "Nombre1", "email1@example.com", "Cuerpo1", posts1);
        commentRepository.save(comment);
        CommentDto expectedCommentDto = new CommentDto();
        expectedCommentDto.setId(comment.getId());
        expectedCommentDto.setName(comment.getName());
        expectedCommentDto.setEmail(comment.getEmail());
        expectedCommentDto.setBody(comment.getBody());

        

        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.ofNullable(comment));



        CommentDto actualCommentDto = commentService.getCommentById(1L, 1L);




        assertEquals(expectedCommentDto, actualCommentDto);
    }

    @Test
    void updateComment(){
        Category category = new Category(1L, "categoria", "descripcion", List.of());
        System.out.println(category);
        Post posts1 = new Post(1L, "titulo del post", "descripcion post", "contenido del post", Set.of(), category);

        category.setPosts(List.of(posts1));
        Comment comment = new Comment(1L, "Nombre1", "email1@example.com", "Cuerpo1", posts1);
        System.out.println(comment);
        Mockito.when(postRepository.save(posts1)).thenReturn(posts1);

        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(posts1));
        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setName("Nombre1");
        commentDto.setEmail("email1@example.com");
        commentDto.setBody("Cuerpo1");

        CommentDto resultado = commentService.updateComment(1L,1L,commentDto);
        assertEquals(commentDto,resultado);

        }

    }





