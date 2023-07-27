package com.ead.authuser.infrastructure.api;

import com.ead.authuser.ControllerTest;
import com.ead.authuser.controllers.UserController;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = UserController.class)
public class UserModelAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;


    @Test
    public void givenAValidId_whenCallsgetOneUser_shouldReturnUserModel() throws Exception {
        // given
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


        Optional<UserModel> userModelOptional = Optional.of(actualUserModel);

        when(userService.findById(any()))
                .thenReturn(userModelOptional);

        // when
        final var request = get("/users/{userId}", actualUserModel.getUserId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.userId", equalTo(actualUserModel.getUserId().toString())))
                .andExpect(jsonPath("$.username", equalTo(expectedUsername)))
                .andExpect(jsonPath("$.email", equalTo(expectedEmail)))
                .andExpect(jsonPath("$.fullName", equalTo(expectedFullName)))
                .andExpect(jsonPath("$.userStatus", equalTo(expectedUserStatus.toString())))
                .andExpect(jsonPath("$.userType", equalTo(expectedUserType.toString())))
                .andExpect(jsonPath("$.phoneNumber", equalTo(expectedPhoneNumber)))
                .andExpect(jsonPath("$.cpf", equalTo(expectedCpf)))
                .andExpect(jsonPath("$.creationDate", equalTo(expectedLastUpdateDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))))
                .andExpect(jsonPath("$.lastUpdateDate", equalTo(expectedLastUpdateDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))))
                );

        verify(userService, times(1)).findById(eq(actualUserModel.getUserId()));
    }


}
