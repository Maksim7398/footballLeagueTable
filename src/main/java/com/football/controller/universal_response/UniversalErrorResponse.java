package com.football.controller.universal_response;

import com.football.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

public class UniversalErrorResponse extends UniversalResponse<ErrorDetails>{

    public UniversalErrorResponse(HttpStatus status,ErrorDetails errorDetails) {
        super(status, null, errorDetails);
    }
}
