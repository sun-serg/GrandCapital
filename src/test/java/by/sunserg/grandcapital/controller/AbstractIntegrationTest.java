package by.sunserg.grandcapital.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Container
        protected static PostgreSQLContainer<?> postgresqlContainer =
                new PostgreSQLContainer<>("postgres:latest");

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            postgresqlContainer.start();
            System.setProperty("SPRING_DATASOURCE_URL", postgresqlContainer.getJdbcUrl());
            System.setProperty("SPRING_DATASOURCE_USERNAME", postgresqlContainer.getUsername());
            System.setProperty("SPRING_DATASOURCE_PASSWORD", postgresqlContainer.getPassword());
        }
    }
}
