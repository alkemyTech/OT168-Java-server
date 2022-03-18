package com.alkemy.ong.web.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(value
                = { IllegalArgumentException.class, IllegalStateException.class })
        protected ResponseEntity<Object> handleConflict(
                RuntimeException ex, WebRequest request) {
            String bodyOfResponse = "El id no existe";
            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.CONFLICT, request);
        }
    }

