package com.smartnotify.config.exception;

import jakarta.persistence.EntityExistsException;

public class CondominiumAlreadyExistsException extends EntityExistsException {

    public CondominiumAlreadyExistsException(final String message) {
        super(message);
    }

}
