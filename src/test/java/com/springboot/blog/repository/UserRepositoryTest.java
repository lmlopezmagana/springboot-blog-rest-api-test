package com.springboot.blog.repository;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test"})
@Testcontainers
@Sql(value = "classpath:insert-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:delete-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

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
    void findByUsername() {
    }

    @Test
    void existsByUsername() {
    }

    //Alejandro Rubens
    @Test
    void existsByEmailTrueStatement() {
        Assertions.assertThat(dataSource).isNotNull();
        Assertions.assertThat(jdbcTemplate).isNotNull();
        Assertions.assertThat(entityManager).isNotNull();
        Assertions.assertThat(userRepository).isNotNull();

        boolean resultTrue = userRepository.existsByEmail("vicente@user.com");

        Assertions.assertThat(resultTrue).isNotNull();
        Assertions.assertThat(resultTrue).isTrue();

    }

    @Test
    void existsByEmailFalseStatement() {
        Assertions.assertThat(dataSource).isNotNull();
        Assertions.assertThat(jdbcTemplate).isNotNull();
        Assertions.assertThat(entityManager).isNotNull();
        Assertions.assertThat(userRepository).isNotNull();

        boolean resultFalse = userRepository.existsByEmail("nonExistingEmail");

        Assertions.assertThat(resultFalse).isNotNull();
        Assertions.assertThat(resultFalse).isFalse();
    }

}