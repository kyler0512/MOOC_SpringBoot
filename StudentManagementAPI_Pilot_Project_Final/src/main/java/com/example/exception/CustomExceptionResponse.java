package com.example.exception;


import java.time.LocalDateTime;

public record CustomExceptionResponse(LocalDateTime timestamp,
                                                   String source,
                                                   String message,
                                                   String stackTrace) {
}