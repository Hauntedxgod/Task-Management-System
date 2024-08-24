package com.example.testeffectivemobile.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserCustomHandlerException {


    @ExceptionHandler({UserNotCreatedException.class})
    public ResponseEntity<Object> handleException(UserNotCreatedException ex) {
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler({UserNotUpdateException.class})
    public ResponseEntity<Object> handleException(UserNotUpdateException ex) {
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.BAD_REQUEST);
    }
}
