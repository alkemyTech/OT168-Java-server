package com.alkemy.ong.domain.exceptions;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message){
        super(message);
    }
}
