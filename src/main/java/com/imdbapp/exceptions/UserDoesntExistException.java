package com.imdbapp.exceptions;

public class UserDoesntExistException extends RuntimeException {
    public String msg;

    public UserDoesntExistException(final String msg) {
        super(msg);
    }

}
