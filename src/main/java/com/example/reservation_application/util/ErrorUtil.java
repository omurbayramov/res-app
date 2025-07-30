package com.example.reservation_application.util;


import com.example.reservation_application.exception.ValidationErrorDto;
import org.springframework.validation.FieldError;

import java.util.List;

import static com.example.reservation_application.util.LocalizationUtil.LOCALIZATION_UTIL;

public enum ErrorUtil {
    ERROR_UTIL;

    public List<ValidationErrorDto> extractValidationErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(ERROR_UTIL::toValidationErrorDto)
                .toList();
    }

    private ValidationErrorDto toValidationErrorDto(FieldError error) {
        var message = LOCALIZATION_UTIL.getMessageByKey(error.getDefaultMessage());
        return new ValidationErrorDto(error.getField(), message);
    }
}