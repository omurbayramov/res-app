package com.example.reservation_application.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        log.error("Illegal argument exception: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
