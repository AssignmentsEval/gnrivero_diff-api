package com.waes.diffapi.domain.exception;

public class InvalidDataValueException extends RuntimeException {

    private static final long serialVersionUID = -4189799611787617210L;

    public InvalidDataValueException(){
        super("Value to compare cannot be null or empty");
    }

}
