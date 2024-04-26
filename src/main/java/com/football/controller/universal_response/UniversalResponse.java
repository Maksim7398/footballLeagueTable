package com.football.controller.universal_response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public abstract class UniversalResponse<T> {

    private final HttpStatus status;

    private final T payload;

    private final T errorDetails;

}
