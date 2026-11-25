package com.campuslands.proyectocoworkhub.presentation.dto;

import jakarta.validation.constraints.*;

public record MemberShipPlanRequestDTO (
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 60, message = "El nombre no puede superar los 60 caracteres")
        String name,

        @NotNull(message = "Las horas incluidas son obligatorias")
        @PositiveOrZero(message = "Las horas no pueden ser negativas")
        Integer includedHoursPerMonth,

        @NotNull(message = "El descuento es obligatorio")
        @Min(value = 0, message = "El descuento mínimo es 0")
        @Max(value = 100, message = "El descuento máximo es 100")
        Integer overageDiscount,

        @NotNull(message = "El máximo de reservas es obligatorio")
        @PositiveOrZero(message = "El máximo de reservas no puede ser negativo")
        Integer maxActiveBookings
){
}
