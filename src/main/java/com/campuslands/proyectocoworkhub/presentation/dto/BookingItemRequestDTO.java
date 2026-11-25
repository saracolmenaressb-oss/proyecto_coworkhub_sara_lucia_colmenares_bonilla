package com.campuslands.proyectocoworkhub.presentation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookingItemRequestDTO (
        @NotNull(message = "El servicio adicional es obligatorio")
        @Positive(message = "El ID del servicio debe ser positivo")
        Long additionalServiceId,

        @Positive(message = "La cantidad debe ser mayor que cero")
        int quantity
){
}
