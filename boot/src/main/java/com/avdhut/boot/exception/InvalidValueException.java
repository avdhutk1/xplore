package com.avdhut.boot.exception;

/**
 * This exception is used to demonstrate the @ExceptionHandler annotation per controller
 * Refer to the controller and the exception method
 */

public class InvalidValueException extends Exception {
    public InvalidValueException(){
        super();
    }

    public InvalidValueException(String message) {
        super(message);
    }

    public InvalidValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
