package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class ResourceNotFound extends RuntimeException {

    public ResourceNotFound() {
        super();
    }

    public ResourceNotFound(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFound(final String message) {
        super(message);
    }

    public ResourceNotFound(final Throwable cause) {
        super(cause);
    }

}