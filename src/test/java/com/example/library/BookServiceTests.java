package com.example.library;

import com.example.library.model.library.Book;
import com.example.library.model.library.Category;
import com.example.library.repository.library.BookRepository;
import com.example.library.repository.library.CategoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class BookServiceTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookRepository bookRepository;

    @LocalServerPort
    private int port;

    private UUID categoryUid;

    @Before
    public void beforeTest() {
        categoryUid = categoryRepository.save(new Category(UUID.randomUUID(), "test")).getUid();
    }

    @After
    public void clean() {
        categoryRepository.deleteAll();
        bookRepository.deleteAll();
        postgreSQLContainer.close();
    }

    private String getPath(String pathVariable) {
        if (pathVariable.isBlank()) return "http://localhost:" + port + "/book";
        else return "http://localhost:" + port + "/book/" + pathVariable;
    }

    @Container
    public static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14.0")
            .withDatabaseName("library")
            .withUsername("test")
            .withPassword("test");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.jpa.hibernate.ddl-auto=create-drop",
                    "spring.datasource.port=" + postgreSQLContainer.getFirstMappedPort(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void crudTest() {

        HttpEntity<Book> request = new HttpEntity<>(new Book(UUID.randomUUID(), "save", "test", new Date(), categoryUid.toString()));
        ResponseEntity<Book> response = restTemplate.exchange(
                getPath(""),
                HttpMethod.POST,
                request,
                Book.class
        );
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertTrue(response.hasBody());
        Assertions.assertEquals("save", Objects.requireNonNull(response.getBody()).getName());

        String bookUid = Objects.requireNonNull(response.getBody()).getUid().toString();

        request = new HttpEntity<>(new Book(UUID.randomUUID(), "update", "test", new Date(), categoryUid.toString()));
        response = restTemplate.exchange(
                getPath(bookUid),
                HttpMethod.POST,
                request,
                Book.class
        );
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertTrue(response.hasBody());
        Assertions.assertEquals("update", Objects.requireNonNull(response.getBody()).getName());

        request = new HttpEntity<>(null);
        response = restTemplate.exchange(
                getPath(bookUid),
                HttpMethod.GET,
                request,
                Book.class
        );
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertTrue(response.hasBody());

        request = new HttpEntity<>(null);
        ResponseEntity<List<Book>> responseList = restTemplate.exchange(
                getPath(""),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        Assertions.assertEquals(200, responseList.getStatusCodeValue());
        Assertions.assertTrue(responseList.hasBody());
        Assertions.assertEquals(1, Objects.requireNonNull(responseList.getBody()).size());

        ResponseEntity<Void> responseVoid = restTemplate.exchange(
                getPath(bookUid),
                HttpMethod.DELETE,
                request,
                Void.class
        );
        Assertions.assertEquals(204, responseVoid.getStatusCodeValue());

        responseList = restTemplate.exchange(
                getPath(""),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        Assertions.assertEquals(200, responseList.getStatusCodeValue());
        Assertions.assertEquals(0, Objects.requireNonNull(responseList.getBody()).size());
    }
}
