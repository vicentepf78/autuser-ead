package com.ead.authuser.validation;

public class Error {

    public String message;

    public Error(String aMessage) {
        this.message = aMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
