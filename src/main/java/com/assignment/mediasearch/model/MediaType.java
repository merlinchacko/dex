package com.assignment.mediasearch.model;

import lombok.Getter;

public enum MediaType {

    BOOK("BOOK"),
    ALBUM("ALBUM");

    @Getter
    private String code;

    MediaType(String code) {
        this.code = code;
    }

}
