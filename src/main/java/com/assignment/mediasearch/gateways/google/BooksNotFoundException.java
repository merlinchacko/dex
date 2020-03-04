package com.assignment.mediasearch.gateways.google;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BooksNotFoundException extends RuntimeException {

    public BooksNotFoundException(String message) {
        super(message);
    }
}
