package com.football.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDetails {

    private final String className;

    private final String message;

    private final LocalDateTime timeStamp;
}
