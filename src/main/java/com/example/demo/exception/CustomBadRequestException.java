package com.example.demo.exception;

public class CustomBadRequestException extends RuntimeException {

    public CustomBadRequestException(String message) {
        super(message);
    }
}
