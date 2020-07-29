package com.avdhut.boot.exception;

/**
 * This is an example of an exception that has no annotations. Hence it is handled by the DefaultHandlerResolver
 * In this resolver, it usually sends a 505 server error http code
 *
 */
public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
