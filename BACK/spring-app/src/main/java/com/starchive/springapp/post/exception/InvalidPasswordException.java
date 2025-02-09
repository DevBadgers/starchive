package com.starchive.springapp.post.exception;

import static com.starchive.springapp.global.ErrorMessage.INVALID_PASSWORD;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super(INVALID_PASSWORD);
    }
    public InvalidPasswordException(String message) {
        super(message);
    }
}
