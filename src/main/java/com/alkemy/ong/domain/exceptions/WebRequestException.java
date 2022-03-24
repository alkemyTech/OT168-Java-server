package com.alkemy.ong.domain.exceptions;

public class WebRequestException extends RuntimeException{
    public WebRequestException(String message){
        super(message);
    }
}
