package com.campuslands.proyectocoworkhub.presentation.dto;

import com.campuslands.proyectocoworkhub.persistence.entity.RoomType;

import java.math.BigDecimal;
import java.util.Set;


public record RoomResponseDTO(
        Long id,
        String name,
        RoomType type,
        int capacity,
        BigDecimal hourlyRate,
        boolean active,
        LocationSummaryDTO location,
        Set<EquipmentResponseDTO> equipments
) {

}
