package com.springboot.blog.Post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.controller.PostController;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    @InjectMocks
    PostController postController;

    @Test
    @WithMockUser(username = "Javi", roles = {"ADMIN"})
    void createPostTest_201() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("title");
        postDto.setContent("content");
        postDto.setDescription("description");
        postDto.setComments(Set.of());
        postDto.setCategoryId(1L);
        when(postService.createPost(Mockito.any(PostDto.class))).thenReturn(postDto);
        mockMvc.perform(post("/api/posts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(postDto)).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
        MvcResult mvcResult = mockMvc.perform(post("/api/posts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(postDto)).accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(postDto));

    }
















































































































































































    
    @Test
    @WithMockUser(username = "Pedro", roles = {"ADMIN"})
    void updatePost_201() throws Exception{
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("title");
        postDto.setContent("sin editar");
        postDto.setDescription("description");
        postDto.setComments(Set.of());
        postDto.setCategoryId(1L);

        PostDto porDtoEdicion = new PostDto();
        porDtoEdicion.setId(1L);
        porDtoEdicion.setTitle("title");
        porDtoEdicion.setContent("editado");
        porDtoEdicion.setDescription("description");
        porDtoEdicion.setComments(Set.of());
        porDtoEdicion.setCategoryId(1L);

        when(postService.updatePost(Mockito.any(PostDto.class), eq(postDto.getId()))).thenReturn(porDtoEdicion);
        mockMvc.perform(put("/api/posts/{id}", postDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.content", is("editado")));

    }

    @Test
    @WithMockUser(username = "Pedro", roles = {"ADMIN"})
    void updatePost_400_tileEmpty() throws Exception{
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setContent("sin editar");
        postDto.setDescription("description");
        postDto.setComments(Set.of());
        postDto.setCategoryId(1L);

        PostDto porDtoEdicion = new PostDto();
        porDtoEdicion.setId(1L);
        porDtoEdicion.setTitle("title");
        porDtoEdicion.setContent("editado");
        porDtoEdicion.setDescription("description");
        porDtoEdicion.setComments(Set.of());
        porDtoEdicion.setCategoryId(1L);

        when(postService.updatePost(Mockito.any(PostDto.class), eq(postDto.getId()))).thenReturn(porDtoEdicion);
        mockMvc.perform(put("/api/posts/{id}", postDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)).accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "Pedro", roles = {"ADMIN"})
    void updatePost_400_postNotExists() throws Exception{
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setContent("sin editar");
        postDto.setDescription("description");
        postDto.setComments(Set.of());
        postDto.setCategoryId(1L);

        PostDto porDtoEdicion = new PostDto();
        porDtoEdicion.setId(0L);
        porDtoEdicion.setTitle("title");
        porDtoEdicion.setContent("editado");
        porDtoEdicion.setDescription("description");
        porDtoEdicion.setComments(Set.of());
        porDtoEdicion.setCategoryId(1L);

        when(postService.updatePost(Mockito.any(PostDto.class), eq(postDto.getId()))).thenReturn(porDtoEdicion);
        mockMvc.perform(put("/api/posts/{id}", postDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}
