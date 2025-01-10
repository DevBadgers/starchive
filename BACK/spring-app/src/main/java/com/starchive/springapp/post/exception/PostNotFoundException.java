package com.starchive.springapp.post.exception;

import static com.starchive.springapp.global.ErrorMessage.POST_NOT_FOUND;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super(POST_NOT_FOUND);
    }
}
