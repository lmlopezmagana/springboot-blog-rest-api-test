package com.springboot.blog.repository;

import com.springboot.blog.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.springboot.blog.repository.config.ConfigTestClass;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

class UserRepositoryTest extends ConfigTestClass{

    @Autowired
    UserRepository userRepository;

    @Test
    void findByEmail() {
    }

    @Test
    void findByUsernameOrEmail() {
        Optional <User> user = userRepository.findByUsernameOrEmail("")
    }

    @Test
    void findByUsername() {
    }

    @Test
    void existsByUsername() {
    }

    @Test
    void existsByEmail() {
    }
}