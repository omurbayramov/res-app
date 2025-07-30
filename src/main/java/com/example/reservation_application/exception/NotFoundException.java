package com.example.reservation_application.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String code) {
        super(code);
    }
}