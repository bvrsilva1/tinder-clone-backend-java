package com.bruno.tinderclone.exception;

import com.bruno.tinderclone.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ApiExceptionHandler<T> {

    @ExceptionHandler(value = { UserNotFoundException.class })
    public ResponseEntity<Response<T>> handleUserNotFound(UserNotFoundException exception, WebRequest request) {
        Response<T> response = new Response<>();
        response.addErrorMessage(exception.getLocalizedMessage());
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
