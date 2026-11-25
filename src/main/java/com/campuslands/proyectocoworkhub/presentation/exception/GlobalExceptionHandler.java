package com.campuslands.proyectocoworkhub.presentation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<Map<String, String>> handleResourceInUse(
            ResourceInUseException exception) {

        Map<String, String> response = new HashMap<>();

        response.put("error", exception.getMessage());
        response.put("codigo", "RESOURCE_IN_USE");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
}
