package com.smartnotify.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerConfig {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CondominiumAlreadyExistsException.class)
    public Map<String, String> handleCondominiumAlreadyExistsException(final CondominiumAlreadyExistsException e) {
        return Collections.singletonMap("error", e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResidentAlreadyExistsException.class)
    public Map<String, String> handleResidentAlreadyExistsException(final ResidentAlreadyExistsException e) {
        return Collections.singletonMap("error", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgumentException(final MethodArgumentNotValidException e) {
        final Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResidenceDetailsNotFoundException.class)
    public Map<String, String> handleResidenceDetailsNotFoundException(final ResidenceDetailsNotFoundException e) {
        return Collections.singletonMap("message", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResidentNotFoundException.class)
    public Map<String, String> handleResidentNotFoundException(final ResidentNotFoundException e) {
        return Collections.singletonMap("message", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParcelNotFoundException.class)
    public Map<String, String> handleParcelNotFoundException(final ParcelNotFoundException e) {
        return Collections.singletonMap("message", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public Map<String, String> handleIllegalStateExceptionException(final IllegalStateException e) {
        return Collections.singletonMap("message", e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public Map<String, String> handleBadCredentialsException(final BadCredentialsException e) {
        return Collections.singletonMap("message", e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public Map<String, String> handleAuthenticationException(final AuthenticationException e) {
        return Collections.singletonMap("message", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(SendParcelNotificationException.class)
    public Map<String, String> handleSendParcelNotificationException(final SendParcelNotificationException e) {
        return Collections.singletonMap("message", e.getMessage());
    }

}