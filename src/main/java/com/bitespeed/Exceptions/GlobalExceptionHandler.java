package com.bitespeed.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bitespeed.ResponseDto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

   // Fallback for unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(new ErrorResponse("BAD_REQUEST", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}