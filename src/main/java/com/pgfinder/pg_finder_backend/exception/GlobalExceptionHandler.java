package com.pgfinder.pg_finder_backend.exception;

import com.pgfinder.pg_finder_backend.dto.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
       Map<String, Object> error = new HashMap<>();
       error.put("timestamp", LocalDateTime.now());
       error.put("status", HttpStatus.BAD_REQUEST.value());
       error.put("message", ex.getMessage());

       return ResponseEntity
               .status(HttpStatus.BAD_REQUEST)
               .body(error);
   }
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationError(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(message));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntime(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(ex.getMessage()));
    }
}


