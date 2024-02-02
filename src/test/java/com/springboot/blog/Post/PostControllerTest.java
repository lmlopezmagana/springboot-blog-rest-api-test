package com.springboot.blog.Post;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    PostService postService;

    ModelMapper modelMapper = new ModelMapper();

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
        when(postService.createPost(any(PostDto.class))).thenReturn(postDto);
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
    @WithMockUser(username = "Javi")
    void createPostTest_401() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("title");
        postDto.setContent("content");
        postDto.setDescription("description");
        postDto.setComments(Set.of());
        postDto.setCategoryId(1L);
        mockMvc.perform(post("/api/posts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(postDto)).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized());
    }

    public static Stream<Arguments> getData() {
        PostDto badTitle = new PostDto();
        badTitle.setCategoryId(1L);
        badTitle.setTitle("h");
        badTitle.setDescription("description dam");
        badTitle.setId(1L);
        badTitle.setContent("f");
        PostDto badDesc= new PostDto();
        badDesc.setCategoryId(1L);
        badDesc.setTitle("hfdasfdsa");
        badDesc.setDescription("d");
        badDesc.setId(1L);
        badDesc.setContent("f");
        PostDto empty = new PostDto();
        return Stream.of(
                Arguments.of(badTitle),
                Arguments.of(badDesc),
                Arguments.of(empty)
        );
    }
    @ParameterizedTest
    @MethodSource({"getData"})
    @WithMockUser(username = "Javi", roles = {"ADMIN"})
    void createPostTest_400(PostDto postDto) throws Exception {

        mockMvc.perform(post("/api/posts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(postDto)).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void getAllPostTest_200() throws Exception {
        PostResponse postResponse = new PostResponse(List.of(), 0, 10, 0, 0, true);
        when(postService.getAllPosts(0, 10, "id", "asc")).thenReturn(postResponse);
        mockMvc.perform(get("/api/posts")
                .contentType("application/json")).andExpect(status().isOk());
        MvcResult mvcResult = mockMvc.perform(get("/api/posts")
                .contentType("application/json")).andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(postResponse));
    }

    @Test
    void getPostById_200() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setContent("content");
        postDto.setComments(Set.of());
        postDto.setId(1L);
        postDto.setDescription("description");
        postDto.setTitle("title");
        postDto.setCategoryId(1L);
        when(postService.getPostById(any(Long.class))).thenReturn(postDto);
        mockMvc.perform(get("/api/posts/1")
                .contentType("application/json")).andExpect(status().isOk());
        MvcResult mvcResult = mockMvc.perform(get("/api/posts/1")
                .contentType("application/json")).andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(postDto));
    }

    @Test
    void getPostById_404() throws Exception {
        PostDto postDto = null;
        when(postService.getPostById(any(Long.class))).thenReturn(postDto);
        mockMvc.perform(get("/api/posts/1")
                .contentType("application/json")).andExpect(status().isNotFound());
    }


}

