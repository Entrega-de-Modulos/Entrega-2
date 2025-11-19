package com.fatec.exceptions;

import com.fatec.dtos.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(DuplicateEmailException ex, HttpServletRequest request) {
        var errorResponse = new ErrorResponse(
            "Duplicate Email",
            400,
            request.getRequestURI(),
            LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        var errorResponse = new ErrorResponse(
            "Invalid email or password",
            401,
            request.getRequestURI(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(401).body(errorResponse);
    }

    @ExceptionHandler({NoResourceFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(Exception ex, HttpServletRequest request) {
        var errorResponse = new ErrorResponse(
            "Resource Not Found",
            404,
            request.getRequestURI(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        var errorResponse = new ErrorResponse(
                "Internal Server Error",
                500,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        LoggerFactory.getLogger(GlobalExceptionHandler.class).error("Unhandled exception", ex);
        return ResponseEntity.status(500).body(errorResponse);
    }
}
