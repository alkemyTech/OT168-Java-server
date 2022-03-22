package com.alkemy.ong.domain.exceptions;

public class BadRequestException extends Exception{

    public BadRequestException (String message){
        super(message);
    }
}
