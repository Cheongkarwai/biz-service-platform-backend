package com.cheong.ecommerce_ai_driven.common.web;

import lombok.Getter;

@Getter
public enum ValidationError {

    EMAIL_ADDRESS_EXISTS("EMAIL_ADDRESS_EXISTS", "Email address already exists", 400),
    MOBILE_NUMBER_EXISTS("MOBILE_NUMBER_EXISTS", "Mobile number already exists", 400);

    private final String code;
    private final String message;
    private final int httpStatus;

    ValidationError(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getFormattedMessage(Object... args) {
        return String.format(message, args);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (HTTP %d)", code, message, httpStatus);
    }

    public ValidationException asException() {
        return new ValidationException(message, code, httpStatus);
    }
}
