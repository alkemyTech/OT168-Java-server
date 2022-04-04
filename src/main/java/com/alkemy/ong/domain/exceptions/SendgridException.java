package com.alkemy.ong.domain.exceptions;

public class SendgridException extends RuntimeException {
    public SendgridException(String message){
        super(message);
    }
}
