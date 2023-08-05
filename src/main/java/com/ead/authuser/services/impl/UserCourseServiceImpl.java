package com.ead.authuser.services.impl;

import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.services.UserCorseService;

public class UserCourseServiceImpl implements UserCorseService {

    private UserCourseRepository userCourseRepository;

    public UserCourseServiceImpl(final UserCourseRepository userCourseRepository) {
        this.userCourseRepository = userCourseRepository;
    }
}
