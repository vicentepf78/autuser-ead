package com.ead.authuser.e2e;

import com.ead.authuser.configuration.json.Json;
import com.ead.authuser.dtos.UserDto;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface MockDsl {

    MockMvc mvc();

    default UUID givenAUser(final String aUserName, final String aEmail, final String aFullName, String aPhoneNumber, String aCpf, String aPassword) throws Exception {

        final UserDto userDto = new UserDto();
        userDto.setUsername(aUserName);
        userDto.setEmail(aEmail);
        userDto.setFullName(aFullName);
        userDto.setPhoneNumber(aPhoneNumber);
        userDto.setCpf(aCpf);
        userDto.setPassword(aPassword);

//        final var aRequestBody = userDto;
        final var actualId = this.given("/auth/signup/", userDto);
        return UUID.fromString(actualId);
    }

    private String given(final String url, final Object body) throws Exception {
        final var aRequest = post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(body));

        final var actualId = this.mvc().perform(aRequest)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse().getHeader("Location")
                .replace("%s/".format(url), "");


        return actualId;
    }

}
