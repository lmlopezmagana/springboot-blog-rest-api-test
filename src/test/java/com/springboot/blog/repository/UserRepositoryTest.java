package com.springboot.blog.repository;

import com.springboot.blog.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.springboot.blog.repository.config.ConfigTestClass;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest extends ConfigTestClass{

    @Autowired
    UserRepository userRepository;

    @Test
    void findByEmailFound() {
        String emailExpected = "fernando@gmail.com";
        Optional<User> userOptional = userRepository.findByEmail(emailExpected);

        assertEquals(userOptional.get().getEmail(), emailExpected);
    }

    @Test
    void findByEmailNotFound(){
        Optional<User> userOptional = userRepository.findByEmail("fakeEmail@gmail.com");

        assertTrue(userOptional.isEmpty());
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