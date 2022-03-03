package com.imdbapp.Exceptions;

public class ApiCallFailedException extends RuntimeException {
    public String msg;

    public ApiCallFailedException(final String msg){
        super(msg);
    }

}
