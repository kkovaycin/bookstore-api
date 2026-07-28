package com.pinsoftstaj.bookstore_api.exception;

public class DuplicateResourceException
        extends RuntimeException {

    public DuplicateResourceException(
            String message
    ) {
        super(message);
    }
}