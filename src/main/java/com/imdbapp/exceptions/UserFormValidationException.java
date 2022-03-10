package com.imdbapp.exceptions;

import java.util.List;

public class UserFormValidationException extends RuntimeException {
    final public List<String> errors;

    public UserFormValidationException(List<String> errors) {
        this.errors = errors;
    }

}
