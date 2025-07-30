package com.example.reservation_application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    UNEXPECTED_ERROR("unexpected.error"),
    VALIDATION_ERROR("validation.error"),
    RESERVATION_NOT_FOUND("not.found-reservation"),
    TABLE_ALREADY_RESERVED("already.reserved-table");

    private final String code;
}
