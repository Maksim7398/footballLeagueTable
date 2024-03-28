package com.football.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDetails {

    private final String className;

    private final String message;

    @JsonFormat(locale = "ru", pattern = "dd MMMM yyyy hh:mm:ss")
    private final LocalDateTime timeStamp;
}
