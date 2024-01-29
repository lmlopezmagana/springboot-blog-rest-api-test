package com.springboot.blog.Post;

import com.springboot.blog.repository.PostRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@DataJpaTest
@ActiveProfiles({"test"})
@Sql(value = {"classpath:import-test-post.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PostRepositoryTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16.0"))
            .withUsername("blog")
            .withPassword("blog")
            .withDatabaseName("postgres-blog");


    @Autowired
    PostRepository repository;

    @ParameterizedTest
    @CsvSource({"1", "2"})
    void findByCategoryIdTest(Long categoryId){
        if(categoryId == 1)
            assertEquals(1, repository.findByCategoryId(categoryId).size());
        else
            assertEquals(0, repository.findByCategoryId(categoryId));
    }
}
