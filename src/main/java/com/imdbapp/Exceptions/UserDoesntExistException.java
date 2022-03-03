package com.imdbapp.Exceptions;

public class UserDoesntExistException extends RuntimeException {
    public String msg;

    public UserDoesntExistException(final String msg){
        super(msg);
    }

}
