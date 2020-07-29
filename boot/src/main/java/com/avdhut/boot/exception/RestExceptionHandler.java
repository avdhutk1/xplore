package com.avdhut.boot.exception;

import com.avdhut.boot.domain.ErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.servlet.http.HttpServletRequest;

/**
 * This is an example of handling exceptions for all controllers
 * All Exception Handling can be in a single place in this class.
 * This is the recommended way as it makes it easy to manage exception handling in one single place
 * Two important things to note
 * 1. the class should extend the ResponseEntityExceptionHandler
 * 2. The method argument of the exception should match the exception mentioned in the annotation
 * It has other standard methods that can be overridden
 * You can also set the order of this exception handler by specifying the @Order annotation
 * */

@Order(1)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(InvalidProductStatusException.class)
    protected ResponseEntity<ErrorInfo> handleInvalidProductStatus(InvalidProductStatusException ex, HttpServletRequest req){

        logger.info("InvalidProductStatusException thrown and is being handled");

        ErrorInfo errorInfo = new ErrorInfo(req.getRequestURL().toString(), ex.getMessage());
        ResponseEntity<ErrorInfo> re = new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
        return re;
    }


}
