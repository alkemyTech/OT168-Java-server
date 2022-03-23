package com.alkemy.ong.domain.exceptions;

public class DifferentIdException extends RuntimeException {

    public DifferentIdException(String message) {
        super(message);
    }

    public DifferentIdException(String messageFormat, Object... args) {
        super(String.format(messageFormat, args));
    }


}