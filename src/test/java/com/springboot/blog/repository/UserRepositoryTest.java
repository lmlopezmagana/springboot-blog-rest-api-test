package com.springboot.blog.repository;

import com.springboot.blog.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.springboot.blog.repository.config.ConfigTestClass;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

class UserRepositoryTest extends ConfigTestClass{

    @Autowired
    private UserRepository userRepository;


    @Test
    void findByEmail() {
    }

    @Test
    void findByUsernameOrEmail() {
    }

    @Test
    void findByUsername() {
        Optional<User> user = userRepository.findByUsername("tvenneur0");
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.get().getUsername(),"tvenneur0");

    }

    @Test
    void existsByUsername() {
    }

    @Test
    void existsByEmail() {
    }
}