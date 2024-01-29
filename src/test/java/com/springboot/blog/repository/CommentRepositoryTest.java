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

        assertNotNull(commentRepository.findByPostId(9));
        assertEquals(2, commentRepository.findByPostId(9).size());
        assertEquals("Suspendisse potenti.", commentRepository.findByPostId(10).get(0).getBody());

    }
}