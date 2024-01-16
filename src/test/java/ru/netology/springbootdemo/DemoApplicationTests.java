package ru.netology.springbootdemo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Container
    private static final GenericContainer<?> myAppFirst = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    @Container
    private static final GenericContainer<?> myAppSecond = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @Test
    void correctDevappResponse() {
        myAppFirst.start();
        String expected = "Current profile is dev";

        Integer port = myAppFirst.getMappedPort(8080);
        String actualBody = restTemplate
                .getForEntity("http://localhost:" + port + "/profile", String.class)
                .getBody();

        assertEquals(expected, actualBody);
    }

    @Test
    void correctOrodappResponse() {
        myAppSecond.start();
        String expected = "Current profile is production";

        Integer port = myAppSecond.getMappedPort(8081);
        String actualBody = restTemplate
                .getForEntity("http://localhost:" + port + "/profile", String.class)
                .getBody();

        assertEquals(expected, actualBody);
    }
}
