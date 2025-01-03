package com.starchive.springapp.category.exception;

import static com.starchive.springapp.global.ErrorMessage.CATEGORY_NOT_FOUND;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super(CATEGORY_NOT_FOUND);
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
