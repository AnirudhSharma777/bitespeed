package com.bitespeed.Exceptions;

public class MethodArgumentNotValidException extends RuntimeException {

    public MethodArgumentNotValidException(String message) {
        super(message);
    }
}
