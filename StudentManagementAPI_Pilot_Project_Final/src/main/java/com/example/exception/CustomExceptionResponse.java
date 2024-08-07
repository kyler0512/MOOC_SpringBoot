package com.example.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.Arrays;

public record CustomExceptionResponse(LocalDateTime timestamp,
                                      int status,
                                      String message,
                                      String stackTrace) {
    public CustomExceptionResponse(HttpStatus status, Exception ex) {
        this(LocalDateTime.now(), status.value(), ex.getMessage(), Arrays.stream(ex.getStackTrace()).findFirst().toString());
    }

    public CustomExceptionResponse( Exception ex) {
        this(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Arrays.stream(ex.getStackTrace()).findFirst().toString());
    }

    public CustomExceptionResponse(String message, Exception ex) {
        this(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), message, Arrays.stream(ex.getStackTrace()).findFirst().toString());
    }

    public CustomExceptionResponse(HttpStatusCode status, String message, Exception ex) {
        this(LocalDateTime.now(), status.value(), message, Arrays.stream(ex.getStackTrace()).findFirst().toString());
    }
}