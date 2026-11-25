package com.campuslands.proyectocoworkhub.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemberShipPlanResponseDTO (
        Long id,
        String name,
        int  includedHoursPerMonth,
        int overageDiscount,
        int  maxActiveBookings
){
}
