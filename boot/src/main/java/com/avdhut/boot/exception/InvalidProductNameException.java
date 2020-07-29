package com.avdhut.boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This is an example of an exception that has the @ResponseStatus annotation in which you can specify
 * the http code that can be returned
 * When this exception is thrown, the resolver looks for this annotation and sends the http code specified in the
 * annotation
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidProductNameException extends Exception {
    public InvalidProductNameException() {
        super();
    }

    public InvalidProductNameException(String message) {
        super(message);
    }

    public InvalidProductNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
