package com.springboot.blog.comments;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    CommentServiceImpl commentService;
    @Autowired
    ObjectMapper objectMapper;
    static Post p = Mockito.mock(Post.class);
    static CommentDto c = new CommentDto();

    String token;

    @BeforeAll
    static void init(){
        c.setName("name");
        c.setBody("body that must be of at least 10 characters");
        c.setEmail("email@email.com");
    }

//    @BeforeEach
//    void setUp() throws Exception{
//        ResultActions resultActions = this.mvc
//                .perform(post("/api/auth/login")
//                        .with(httpBasic("Tom","12345")));
//        MvcResult mvcResult = resultActions.andDo(print()).andReturn();
//        String contentAsString = mvcResult.getRequest().getContentAsString();
//        JSONObject json = new JSONObject(contentAsString);
//        token = "Bearer" + json.getJSONObject("data").getString("token");
//    }


    @Test
    @WithMockUser(username="Tom", roles = {"USER","ADMIN"})
    void createCommentTest() throws Exception {
        when(commentService.createComment(p.getId(),c)).thenReturn(c);

        mvc.perform(post("/api/v1/posts/{postId}/comments",p.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(c)))
//                        .header("Authorization",token))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.name",is("name")));
    }
}