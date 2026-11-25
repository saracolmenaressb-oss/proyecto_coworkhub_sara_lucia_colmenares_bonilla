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

    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(
            org.springframework.security.core.AuthenticationException exception) {

        Map<String, Object> response = new HashMap<>();

        response.put("error", "Usuario o contraseña incorrectos");
        response.put("codigo", 401);
        response.put("timestamp", java.time.OffsetDateTime.now().toString());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .header(
                        org.springframework.http.HttpHeaders.WWW_AUTHENTICATE,
                        "Bearer"
                )
                .body(response);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(
            org.springframework.security.access.AccessDeniedException exception) {

        Map<String, Object> response = new HashMap<>();

        response.put("error", "No tienes permisos para realizar esta operación");
        response.put("codigo", 403);
        response.put("timestamp", java.time.OffsetDateTime.now().toString());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }
}
