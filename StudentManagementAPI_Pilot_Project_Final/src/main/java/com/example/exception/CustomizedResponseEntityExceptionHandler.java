package com.example.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.util.Locale.ENGLISH;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public CustomizedResponseEntityExceptionHandler() {
        super();
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final RuntimeException ex, final WebRequest request) {
        final CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(ex);
        return handleExceptionInternal(ex, exceptionResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        if (request instanceof ServletWebRequest servletWebRequest) {
            HttpServletResponse response = servletWebRequest.getResponse();
            if (response != null && response.isCommitted()) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Response already committed. Ignoring: " + ex);
                }
                return null;
            }
        }

        if (statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        if (body == null && ex instanceof ErrorResponse errorResponse) {
            body = new CustomExceptionResponse( errorResponse.getStatusCode(), HttpStatus.valueOf(errorResponse.getStatusCode().value()).getReasonPhrase(),ex);
        }

        return createResponseEntity(body, headers, statusCode, request);
    }



    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final WebRequest request) {
        final CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(ex);
        return handleExceptionInternal(ex, exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ NoSuchElementException.class })
    public ResponseEntity<Object> handleBadRequest(final NoSuchElementException ex, final WebRequest request) {
        final CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(HttpStatus.NOT_FOUND,ex);
        return handleExceptionInternal(ex, exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
        final CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(ex);
        return handleExceptionInternal(ex, exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(ex);
        return handleExceptionInternal(ex, exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        final CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(String.join(", ", errors), ex);
        return handleExceptionInternal(ex, exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    // 404

    @ExceptionHandler(value = { EntityNotFoundException.class, ResourceNotFound.class })
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        final CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(ex);
        return handleExceptionInternal(ex, exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // 409

    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class })
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        final CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(HttpStatus.CONFLICT, ex);
        return handleExceptionInternal(ex, exceptionResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // 412

    // 500

    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    /*500*/public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        final CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        return handleExceptionInternal(ex, exceptionResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}