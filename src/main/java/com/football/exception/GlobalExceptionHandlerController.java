package com.football.exception;

import com.football.controller.universal_response.UniversalErrorResponse;
import com.football.controller.universal_response.UniversalResponse;
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
    public ResponseEntity<UniversalResponse<ErrorDetails>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ErrorDetails errorDetails = new ErrorDetails(
                exception.getClass().getSimpleName(),
                exception.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining("; ")),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(new UniversalErrorResponse(HttpStatus.BAD_REQUEST,errorDetails),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UniversalResponse<ErrorDetails>> handleTeamNotFoundException(TeamNotFoundException exception) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getClass().getSimpleName(), exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(new UniversalErrorResponse(HttpStatus.BAD_REQUEST,errorDetails),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UniversalResponse<ErrorDetails>> handleThisTeamAlreadyCompeted(MatchExceptions exception) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getClass().getSimpleName(), exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(new UniversalErrorResponse(HttpStatus.BAD_REQUEST,errorDetails),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UniversalResponse<ErrorDetails>> handleTournamentNotFoundException(TournamentNotFoundException exception) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getClass().getSimpleName(), exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(new UniversalErrorResponse(HttpStatus.BAD_REQUEST,errorDetails),
                HttpStatus.BAD_REQUEST);
    }
}