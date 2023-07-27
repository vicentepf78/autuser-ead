package com.ead.authuser.domain;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.exceptions.DomainException;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserModelTests {

    @Test
    public void givenAValidParams_whenCallNewUserModel_thenInstantiateAUserModel() {
        final var expectedUsername = "carlosAlberto";
        final var expectedEmail = "carlosalberto@gmail.com";
        final var expectedPassword = "123456";
        final var expectedFullName = "Carlos Alberto Silva";
        final var expectedUserType = UserType.STUDENT;
        final var expectedUserStatus = UserStatus.ACTIVE;
        final var expectedPhoneNumber = "4599998801";
        final var expectedCpf = "32456711099";
        final String expectedImageUrl = null;
        final LocalDateTime expectedCreationDate = LocalDateTime.now();
        final LocalDateTime expectedLastUpdateDate = LocalDateTime.now();

        final var actualUserModel = UserModel.with(
                expectedUsername,
                expectedEmail,
                expectedPassword,
                expectedFullName,
                expectedUserStatus,
                expectedUserType,
                expectedPhoneNumber,
                expectedCpf,
                expectedImageUrl,
                expectedCreationDate,
                expectedLastUpdateDate
        );

//        actualUserModel.setUserId(userId);
//        actualUserModel.setUsername(expectedUsername);
//        actualUserModel.setEmail(expectedEmail);
//        actualUserModel.setPassword(expectedPassword);
//        actualUserModel.setFullName(expectedFullName);
//        actualUserModel.setUserStatus(expectedUserStatus);
//        actualUserModel.setUserType(expectedUserType);
//        actualUserModel.setPhoneNumber(expectedPhoneNumber);
//        actualUserModel.setCpf(expectedCpf);
//        actualUserModel.setImageUrl(expectedImageUrl);
//        actualUserModel.setCreationDate(expectedCreationDate);
//        actualUserModel.setLastUpdateDate(expectedLastUpdateDate);


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

    @Test
    public void givenAnInvalidNullUserName_whenCallNewUserModelAndValidate_thenShouldReceiveError() {
        final String expectedUsername = null;
        final var expectedEmail = "carlosalberto@gmail.com";
        final var expectedPassword = "123456";
        final var expectedFullName = "Carlos Alberto Silva";
        final var expectedUserType = UserType.STUDENT;
        final var expectedUserStatus = UserStatus.ACTIVE;
        final var expectedPhoneNumber = "4599998801";
        final var expectedCpf = "32456711099";
        final String expectedImageUrl = null;
        final LocalDateTime expectedCreationDate = LocalDateTime.now();
        final LocalDateTime expectedLastUpdateDate = LocalDateTime.now();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'username' should not be null";

        final var actualUserModel = UserModel.with(
                expectedUsername,
                expectedEmail,
                expectedPassword,
                expectedFullName,
                expectedUserStatus,
                expectedUserType,
                expectedPhoneNumber,
                expectedCpf,
                expectedImageUrl,
                expectedCreationDate,
                expectedLastUpdateDate
        );

        final var actualException =
                assertThrows(DomainException.class, () -> actualUserModel.validate(new ThrowsValidationHandler()));


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

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).getMessage());
    }

    @Test
    public void whenExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt("1a");
        });

        String expectedMessage = "For input string";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
