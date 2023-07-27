package com.ead.authuser.e2e;

import com.ead.authuser.E2ETest;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.UUID;

@E2ETest
@Testcontainers
public class UserE2ETest implements MockDsl {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Container
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer("postgres:latest")
            .withPassword("12345")
            .withUsername("postgres")
            .withDatabaseName("ead-authuser");

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("postgresql.port", () -> POSTGRE_SQL_CONTAINER.getMappedPort(5432));
    }

    @Override
    public MockMvc mvc() {
        return this.mvc;
    }


    @Test
    public void asAAdminIShouldBeAbleToCreateANewUserWithValidValues() throws Exception {
        Assertions.assertTrue(POSTGRE_SQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, userRepository.count());

        final var expectedUsername = "carlosAlberto";
        final var expectedEmail = "carlosalberto@gmail.com";
        final var expectedPassword = "12345678";
        final var expectedFullName = "Carlos Alberto Silva";
        final var expectedUserType = UserType.STUDENT;
        final var expectedUserStatus = UserStatus.ACTIVE;
        final var expectedPhoneNumber = "4599998801";
        final var expectedCpf = "32456711099";
//        final String expectedImageUrl = null;
//        final LocalDateTime expectedCreationDate = LocalDateTime.now();
//        final LocalDateTime expectedLastUpdateDate = LocalDateTime.now();

        final var actualId = givenAUser(expectedUsername, expectedEmail, expectedFullName, expectedPhoneNumber, expectedCpf, expectedPassword);

        final var actualUserModel = userRepository.findById(actualId).get();

        Assertions.assertNotNull(actualUserModel);
        Assertions.assertNotNull(actualUserModel.getUserId());
        Assertions.assertEquals(expectedUsername, actualUserModel.getUsername());
        Assertions.assertEquals(expectedEmail, actualUserModel.getEmail());
        Assertions.assertEquals(expectedPhoneNumber, actualUserModel.getPhoneNumber());
        Assertions.assertEquals(expectedFullName, actualUserModel.getFullName());
        Assertions.assertEquals(expectedUserType, actualUserModel.getUserType());
        Assertions.assertEquals(expectedUserStatus, actualUserModel.getUserStatus());
        Assertions.assertEquals(expectedCpf, actualUserModel.getCpf());
        Assertions.assertNull(actualUserModel.getImageUrl());
        Assertions.assertNotNull(actualUserModel.getCreationDate());
        Assertions.assertNotNull(actualUserModel.getLastUpdateDate());
    }

}
