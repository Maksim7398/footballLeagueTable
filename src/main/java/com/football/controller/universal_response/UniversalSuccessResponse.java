package com.football.controller.universal_response;

import org.springframework.http.HttpStatus;

public class UniversalSuccessResponse<T> extends UniversalResponse<T> {
    public UniversalSuccessResponse(HttpStatus status, T payload) {
        super(status, payload, null);
    }
}
