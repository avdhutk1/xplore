package com.avdhut.boot.exception;

/**
 * This example demonstrates teh global exception for all controllers
 * See the RestExceptionHandler class for details
 */

public class InvalidProductStatusException extends Exception {
    public InvalidProductStatusException() {
        super();
    }

    public InvalidProductStatusException(String message) {
        super(message);
    }

    public InvalidProductStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
