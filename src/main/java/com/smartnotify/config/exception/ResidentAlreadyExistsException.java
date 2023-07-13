package com.smartnotify.config.exception;

import jakarta.persistence.EntityExistsException;

public class ResidentAlreadyExistsException extends EntityExistsException {

    public ResidentAlreadyExistsException(final String message) {
        super(message);
    }

}
