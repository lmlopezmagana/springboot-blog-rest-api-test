package com.springboot.blog.repository;

import com.springboot.blog.entity.User;
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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test"})
@Testcontainers
@Sql(value = "classpath:insert-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:delete-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
            .withUsername("testUser")
            .withPassword("testSecret")
            .withDatabaseName("testDatabase");

    @Test
    void findByEmail() {
    }

    @Test
    void findByUsernameOrEmail() {
    }

    @Test
    void WhenExistingUserNameThenReturnUser() {
        String username = "marcopp";
        Optional<User> user = userRepository.findByUsername(username);
        assertTrue(user.isPresent());
        assertEquals("marco", user.get().getName());
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
        String username = "marcopp";
        Boolean user = userRepository.existsByUsername(username);
        assertTrue(user);
    }
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