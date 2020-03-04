package com.assignment.mediasearch.gateways.iTunes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AlbumsNotFoundException extends RuntimeException {

    public AlbumsNotFoundException(String message) {
        super(message);
    }
}
