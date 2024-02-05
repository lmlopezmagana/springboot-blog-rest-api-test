package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import org.hibernate.mapping.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @InjectMocks
    PostServiceImpl postService;
    @Spy
    ModelMapper modelMapper;
    @Mock
    CategoryRepository categoryRepository;

    @Mock
    PostRepository postRepository;


    /*
    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        // convert DTO to entity
        Post post = mapToEntity(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);

        // convert entity to DTO
        PostDto postResponse = mapToDTO(newPost);
        return postResponse;
    }
    este metodo recibe un postdto que solo tiene el dide la categoria la busca transfdorma el dto a post y si encuentra esa categria se la setea
    para guardarla en la base de datos  depues vuelve a transdormar la entidad a dto y la devielve
     */
    //Marco Pertegal
    @Test
    void whenPostDtoThenCreateNewPost() {
        Long categoryId = 3L;
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("Mi mejor verano");
        postDto.setDescription("El año pasado fui a Marruecos a hacer turismo");
        postDto.setContent("Mucho texto y muchas imagenes");
        postDto.setComments(new HashSet<>());
        postDto.setCategoryId(3L);
        Category category = new Category(categoryId, "Vacaciones","Mis vacaciones", List.of());
        Post post = new Post(1L, "Sample Title", "Sample Description", "Sample Content", new HashSet<>(), category);

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(postService.mapToDTO(post)).thenReturn(postDto);
        Mockito.when(postService.mapToEntity(postDto)).thenReturn(post);
        Mockito.when(postRepository.save(post)).thenReturn(post);

        Optional <Category> result = categoryRepository.findById(categoryId);
        assertNotNull(result);
        assertEquals("Vacaciones", result.get().getName());

        PostDto createdPost = postService.createPost(postDto);
        assertNotNull(createdPost);
        assertEquals("Mi mejor verano", createdPost.getTitle());
        assertEquals("Mucho texto y muchas imagenes", createdPost.getContent());

        Mockito.verify(postRepository).save(post);
    }

    //Marco Pertegal
    @Test
    void whenCategoryIdNotFoundThenThrowException(){
        Long categoryId = 3L;
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("Mi mejor verano");
        postDto.setDescription("El año pasado fui a Marruecos a hacer turismo");
        postDto.setContent("Mucho texto y muchas imagenes");
        postDto.setComments(new HashSet<>());
        postDto.setCategoryId(categoryId);

        Mockito.when(categoryRepository.findById(postDto.getCategoryId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class,()->{
            postService.createPost(postDto);
        });
        assertEquals("Category not found with id : '"+ categoryId+"'", exception.getMessage());
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