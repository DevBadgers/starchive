package com.starchive.springapp.hashtag.exception;

import static com.starchive.springapp.global.ErrorMessage.HASHTAG_NOT_FOUND;

public class HashTagNotFoundException extends RuntimeException {
    public HashTagNotFoundException() {
        super(HASHTAG_NOT_FOUND);
    }

    public HashTagNotFoundException(String message) {
        super(message);
    }
}
