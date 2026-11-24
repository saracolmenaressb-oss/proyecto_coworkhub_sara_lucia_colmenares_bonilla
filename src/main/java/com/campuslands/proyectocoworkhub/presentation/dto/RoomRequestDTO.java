package com.campuslands.proyectocoworkhub.presentation.dto;

import com.campuslands.proyectocoworkhub.persistence.entity.RoomType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Set;

public record RoomRequestDTO (
        @NotBlank
        String name,

        @NotNull
        RoomType type,

        @Positive
        int capacity,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal hourlyRate,

        @NotNull
        Long locationId,

        Set<Long> equipmentIds
){
}
