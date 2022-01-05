package com.softserve.clinic.exception;

public class NullEntityReferenceException extends RuntimeException {

    public NullEntityReferenceException() {
    }

    public NullEntityReferenceException(String message) {
        super(message);
    }
}
