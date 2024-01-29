package com.springboot.blog.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.springboot.blog.repository.config.ConfigTestClass;
import org.springframework.beans.factory.annotation.Autowired;

class CommentRepositoryTest extends ConfigTestClass{

    @Autowired
    CommentRepository commentRepository;

    @Test
    void findByPostId() {

        assertEquals("Comentario 1", commentRepository.findByPostId(1).stream().findFirst().get().getName());
        assertTrue();

    }
}