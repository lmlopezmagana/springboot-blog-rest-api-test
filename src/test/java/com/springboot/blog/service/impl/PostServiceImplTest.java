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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @InjectMocks
    PostServiceImpl postService;

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
        Long categoryId = 1L;
        PostDto postdto = new PostDto(1L, "a", "b", "c", Set.of(), 2L);
        Category category = new Category(categoryId, "Partituras","Partituras de la banda de música", List.of());

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        PostDto result = postService.createPost(postdto);
        assertNotNull(result);
        assertEquals(postdto.getTitle(), result.getTitle());

    }

    //Marco Pertegal
    @Test
    void whenCategoryIdNotFoundThenThrowException(){
        Long categoryId = 3L;
        PostDto postdto = new PostDto(1L, "a", "b", "c", Set.of(), categoryId);

        Mockito.when(postService.createPost(postdto)).thenReturn(postdto);
        Mockito.when(postRepository.findById(categoryId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class);

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