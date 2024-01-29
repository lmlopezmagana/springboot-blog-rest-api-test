package com.springboot.blog.repository;

import com.springboot.blog.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test"})
@Testcontainers
@Sql(value = "classpath:insert-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:delete-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
            .withUsername("testUser")
            .withPassword("testSecret")
            .withDatabaseName("testDatabase");

    //Cristian Pulido
    @Test
    void findByEmail() {
        Optional<User> encontradofalse = userRepository.findByEmail("oliva0@europa.eu");
        Optional<User> encontradotrue = userRepository.findByEmail("loliva0@europa.eu");

        assertTrue(encontradofalse.isEmpty(),"No se ha encontrado");
        assertTrue(encontradotrue.isPresent(),"Se ha encontrado");
    }

    //Sebastián Millán
    @Test
    void whenUsernameIsPresentAndEmailIsPresent_thenUserIsPresent() {
        Optional<User> result = userRepository.findByUsernameOrEmail("lcroxton0", "loliva0@europa.eu");
        assertTrue(result.isPresent());
        assertEquals(result.get().getUsername(),"lcroxton0");
        assertEquals(result.get().getEmail(),"loliva0@europa.eu");
    }

    //Marco Pertegal
    @Test
    void WhenExistingUserNameThenReturnUser() {
        String username = "fspincke1";
        Optional<User> user = userRepository.findByUsername(username);
        assertTrue(user.isPresent());
        assertEquals("Faunie", user.get().getName());
    }

    @Test
    void WhenNonExistingUserNameThenReturnEmpty() {
        String username = "a";
        Optional<User> user = userRepository.findByUsername(username);
        assertFalse(user.isPresent());
        assertTrue(user.isEmpty());
    }

    @Test
    void WhenExistingUserNameThenReturnTrue() {
        String username = "fspincke1";
        Boolean user = userRepository.existsByUsername(username);
        assertTrue(user);
    }

    //Marco Pertegal
    @Test
    void WhenNonExistingUserNameThenReturnFalse() {
        String username = "a";
        Boolean user = userRepository.existsByUsername(username);
        assertFalse(user);
    }

    @Test
    void existsByEmail() {
    }
}