package com.campuslands.proyectocoworkhub.presentation.dto;

import java.math.BigDecimal;

public record AdditionalServiceResponseDTO(
        Long id,
        String name,
        BigDecimal unitPrice
) {
}
