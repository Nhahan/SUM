package com.abitly.exception;

public class ApiRequestException extends IllegalArgumentException {
    public ApiRequestException(String message) {
        super(message);
    }
}