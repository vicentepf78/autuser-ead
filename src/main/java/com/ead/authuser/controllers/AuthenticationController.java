package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.function.Function;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated(UserDto.UserView.RegistrationPost.class)
                                               @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {
        if (userService.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken!");
        }
        if (userService.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken!");
        }

        var userModel = UserModel.with(
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getPassword(),
//                userDto.getFullName() != null ? userDto.getFullName() : "FullName Default",
                userDto.getFullName(),
                UserStatus.ACTIVE,
                UserType.STUDENT,
                userDto.getPhoneNumber(),
                userDto.getCpf(),
                userDto.getImageUrl(),
                LocalDateTime.now(ZoneId.of("UTC")),
                LocalDateTime.now(ZoneId.of("UTC"))
                );

//        BeanUtils.copyProperties(userDto, userModel);
//        userModel.setUserStatus(UserStatus.ACTIVE);
//        userModel.setUserType(UserType.STUDENT);
//        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
//        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

//        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
//                ResponseEntity.created(URI.create("/categories/" + output.id())).body(output);

        userService.save(userModel);
//        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
        return ResponseEntity.created(URI.create("/auth/signup/" + userModel.getUserId())).body(userModel);
    }

}
