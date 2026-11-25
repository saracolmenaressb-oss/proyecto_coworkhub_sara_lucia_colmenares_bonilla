package com.campuslands.proyectocoworkhub.presentation.exception;

public class ResourceInUseException extends RuntimeException{
    public ResourceInUseException(String message) {
        super(message);
    }
}
