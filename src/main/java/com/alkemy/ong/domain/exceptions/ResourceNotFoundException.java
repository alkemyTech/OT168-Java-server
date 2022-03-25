package com.alkemy.ong.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String messageFormat, Object... args) {
        super(String.format(messageFormat, args));
    }

    public ResourceNotFoundException(Long id, String entity) {
        super("No " + entity + " found with ID " + id);
    }

    public ResourceNotFoundException() {

    }
}