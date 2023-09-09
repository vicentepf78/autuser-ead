package com.ead.authuser.controllers;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    private final CourseClient courseClient;

    private final UserService userService;


    public UserCourseController(final CourseClient courseClient,
                                final UserService userService) {
        this.courseClient = courseClient;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Object> getAllCoursesByUser(@PageableDefault(sort = "courseId", direction = Sort.Direction.ASC) final Pageable pageable,
                                                      @PathVariable(value = "userId") final UUID userId,
                                                      @RequestHeader("Authorization") String token){
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable, token));
    }



}
