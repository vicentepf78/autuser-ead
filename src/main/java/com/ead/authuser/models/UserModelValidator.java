package com.ead.authuser.models;

import com.ead.authuser.validation.Error;
import com.ead.authuser.validation.ValidationHandler;
import com.ead.authuser.validation.Validator;

public class UserModelValidator extends Validator {
    public static final int USERNAME_MAX_LENGTH = 50;
    public static final int USERNAME_MIN_LENGTH = 6;
    private final UserModel userModel;

    public UserModelValidator(final UserModel aUserModel, final ValidationHandler aHandler) {
        super(aHandler);
        this.userModel = aUserModel;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var username = this.userModel.getUsername();
        if (username == null) {
            this.validationHandler().append(new Error("'username' should not be null"));
            return;
        }

        if (username.isBlank()) {
            this.validationHandler().append(new Error("'username' should not be empty"));
            return;
        }

        final int length = username.trim().length();
        if (length > USERNAME_MAX_LENGTH || length < USERNAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'username' must be between 6 and 50 characters"));
        }
    }
}
