package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    PostServiceImpl postService;

    @Test
    void createPost() {
    }

    @Test
    void getAllPosts_OrderAsc() {
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "title";
        String sortDir = "ASC";

        Sort sort = Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Alex");

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Pepe");

        List<Post> postList = List.of(post2, post1);
        Page<Post> postPage = new PageImpl<>(postList);

        Mockito.when(postRepository.findAll(Mockito.eq(pageable))).thenReturn(postPage);

        PostDto postDto = new PostDto();
        postDto.setId(post2.getId());
        postDto.setTitle(post2.getTitle());

        PostDto postDto2 = new PostDto();
        postDto2.setId(post1.getId());
        postDto2.setTitle(post1.getTitle());

        List<PostDto> content= List.of(postDto, postDto2);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(PostDto.class)))
                .thenReturn(content.get(0))
                .thenReturn(content.get(1));

        PostResponse result = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);

        assertEquals(1 , result.getTotalPages());
        assertEquals(2 , result.getTotalElements());
        assertEquals(2L, result.getContent().get(0).getId());
    }

    @Test
    void getAllPosts_OrderDesc() {
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "title";
        String sortDir = "DESC";

        Sort sort = Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Alex");

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Pepe");

        List<Post> postList = List.of(post1, post2);
        Page<Post> postPage = new PageImpl<>(postList);

        Mockito.when(postRepository.findAll(Mockito.eq(pageable))).thenReturn(postPage);

        PostDto postDto = new PostDto();
        postDto.setId(post1.getId());
        postDto.setTitle(post1.getTitle());

        PostDto postDto2 = new PostDto();
        postDto2.setId(post2.getId());
        postDto2.setTitle(post2.getTitle());

        List<PostDto> content= List.of(postDto, postDto2);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(PostDto.class)))
                .thenReturn(content.get(0))
                .thenReturn(content.get(1));

        PostResponse result = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);

        assertEquals(1 , result.getTotalPages());
        assertEquals(2 , result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getId());
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