package com.imdbapp.exceptions;

public class ApiCallFailedException extends RuntimeException {
    public String msg;

    public ApiCallFailedException(final String msg) {
        super(msg);
    }

}
