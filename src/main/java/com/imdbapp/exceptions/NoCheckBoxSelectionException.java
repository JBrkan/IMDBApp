package com.imdbapp.exceptions;

public class NoCheckBoxSelectionException extends RuntimeException {
    final public String msg;

    public NoCheckBoxSelectionException(String msg) {
        this.msg = msg;
    }
}
