package com.abitly.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ApiException {
    private String message;
    private HttpStatus httpStatus;


    public ApiException(String message, HttpStatus badRequest) {
        this.message =message;
        this.httpStatus = badRequest;
    }
}