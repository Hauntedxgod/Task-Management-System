package com.example.testeffectivemobile.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskCustomHandlerException {


    @ExceptionHandler(TaskErrorException.class)
    public ResponseEntity<String> handleTaskNotFoundException(TaskErrorException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @ExceptionHandler(TaskNotCreatedException.class)
    public ResponseEntity<String> handleTaskNotFoundException(TaskNotCreatedException ex) {
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(ex.getMessage());
    }

    @ExceptionHandler(TaskNotUpdateException.class)
    public ResponseEntity<String> handleTaskNotFoundException(TaskNotUpdateException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
