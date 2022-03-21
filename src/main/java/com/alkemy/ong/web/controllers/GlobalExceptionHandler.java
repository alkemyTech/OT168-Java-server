package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(value
                = { ResourceNotFoundException.class, ResourceNotFoundException.class })
        protected ResponseEntity<Object> handleConflict(
                RuntimeException ex, WebRequest request) {
            return handleExceptionInternal(ex, ex.getMessage(),
                    new HttpHeaders(), HttpStatus.NOT_FOUND, request);
        }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> message = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(m->m.getDefaultMessage())
                .collect(toList());
        return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
    }
}