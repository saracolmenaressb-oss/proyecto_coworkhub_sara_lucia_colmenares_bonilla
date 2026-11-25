package com.campuslands.proyectocoworkhub.presentation.dto;

import java.math.BigDecimal;

public record BookingItemResponseDTO(
        Long id,
        Long additionalServiceId,
        String additionalServiceName,
        int quantity,
        BigDecimal appliedUnitPrice,
        BigDecimal subtotal
) {
}
