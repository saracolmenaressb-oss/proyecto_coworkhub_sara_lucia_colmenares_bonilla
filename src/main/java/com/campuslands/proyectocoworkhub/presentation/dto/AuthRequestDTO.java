package com.campuslands.proyectocoworkhub.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO (
        @NotBlank(message = "El nombre de usuario es obligatorio")
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        String password
){
}
