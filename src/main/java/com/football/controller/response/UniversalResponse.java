package com.football.controller.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class UniversalResponse {

    private HttpStatus status;

    private Object payload;

    private Object errorDetails;

}
