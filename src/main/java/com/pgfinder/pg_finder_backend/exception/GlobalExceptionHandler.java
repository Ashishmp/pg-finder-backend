package com.pgfinder.pg_finder_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
       Map<String, Object> error = new HashMap<>();
       error.put("timestamp", LocalDateTime.now());
       error.put("status", HttpStatus.UNAUTHORIZED.value());
       error.put("message", ex.getMessage());

       return ResponseEntity
               .status(HttpStatus.UNAUTHORIZED)
               .body(error);
   }
}