package com.springboot.blog.Comment;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.impl.CommentServiceImpl;
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

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;
    private ModelMapper mapper;
    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void createComent(CommentDto commentDto){
        Category category = new Category(1L,"categoria","descripcion",List.of());
        Post posts1 = new Post(1L,"titulo del post","descripcion post","contenido del post",Set.of(),category);
        Post posts2 = new Post(2L,"titulo del post","descripcion post","contenido del post",Set.of(),category);
        Post posts3 = new Post(3L,"titulo del post","descripcion post","contenido del post",Set.of(),category);
        Comment comments= new Comment(1L,"nombre del comentario","email del comment","cuerpo del comment",posts1);
         comments = mapper.map(commentDto, Comment.class);
         comments.setPost(posts1);

        category.setPosts(List.of(posts1,posts2,posts3));
        posts1.setComments(Set.of(comments));
        posts2.setComments(Set.of(comments));
        posts3.setComments(Set.of(comments));

        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(posts1));
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(comments);

        Comment newComment = commentRepository.save(comments);

        commentDto= commentService.createComment(1L,commentDto);

        assertEquals(1L,commentDto.);







    }


}
