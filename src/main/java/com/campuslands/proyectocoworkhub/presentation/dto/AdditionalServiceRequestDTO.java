package com.campuslands.proyectocoworkhub.presentation.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record AdditionalServiceRequestDTO (
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 80, message = "El nombre no puede superar los 80 caracteres")
        String name,

        @NotNull(message = "El precio unitario es obligatorio")
        @DecimalMin(value = "0.00", message = "El precio no puede ser negativo")
        @Digits(integer = 8, fraction = 2,
                message = "El precio debe tener máximo 8 enteros y 2 decimales")
        BigDecimal unitPrice
){
}
