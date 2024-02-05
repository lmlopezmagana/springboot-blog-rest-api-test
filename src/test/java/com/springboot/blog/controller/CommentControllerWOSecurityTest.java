package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentControllerWOSecurityTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @InjectMocks
    private CommentController commentController;
    @MockBean
    private CommentServiceImpl commentService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private WebApplicationContext webApplicationContext;


    @Test
    public void deleteComment_ValidComment_ReturnsOk() throws Exception {
        Long postId = 1L;
        Long commentId = 2L;

        // Mockear el comportamiento del servicio
        doNothing().when(commentService).deleteComment(postId, commentId);

        // Realizar la petición DELETE al endpoint del controlador
        mockMvc.perform(delete("/api/comments/{postId}/comments/{commentId}", postId, commentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment deleted successfully"));

        // Verificar que el método deleteComment del servicio fue llamado
        verify(commentService, times(1)).deleteComment(postId, commentId);
    }

}
