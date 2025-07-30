package com.example.reservation_application.exception;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String code) {
        super(code);
    }
}