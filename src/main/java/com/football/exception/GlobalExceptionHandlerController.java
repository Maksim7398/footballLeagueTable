package com.football.exception;

import com.football.controller.response.UniversalResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler
    public ResponseEntity<UniversalResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ErrorDetails errorDetails = new ErrorDetails(
                exception.getClass().getSimpleName(),
                exception.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining("; ")),
                LocalDateTime.now()
        );
        UniversalResponse universalResponse = UniversalResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorDetails(errorDetails)
                .payload(null)
                .build();
        return new ResponseEntity<>(universalResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UniversalResponse> handleTeamNotFoundException(TeamNotFoundException exception) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getClass().getSimpleName(), exception.getMessage(), LocalDateTime.now());
        UniversalResponse universalResponse = UniversalResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorDetails(errorDetails)
                .payload(null)
                .build();
        return new ResponseEntity<>(universalResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UniversalResponse> handleThisTeamAlreadyCompeted(MatchExceptions exception) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getClass().getSimpleName(), exception.getMessage(), LocalDateTime.now());
        UniversalResponse universalResponse = UniversalResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorDetails(errorDetails)
                .payload(null)
                .build();
        return new ResponseEntity<>(universalResponse, HttpStatus.BAD_REQUEST);
    }
}