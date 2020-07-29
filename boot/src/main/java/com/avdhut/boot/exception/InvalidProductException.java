package com.avdhut.boot.exception;

/**
 * This example is used for demonstrating defaultHandling and also jsr330 validation
 */

public class InvalidProductException extends Exception {
    public InvalidProductException() {
        super();
    }

    public InvalidProductException(String message) {
        super(message);
    }

    public InvalidProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
