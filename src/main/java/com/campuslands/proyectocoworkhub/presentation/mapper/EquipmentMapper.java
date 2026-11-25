package com.campuslands.proyectocoworkhub.presentation.mapper;

import com.campuslands.proyectocoworkhub.persistence.entity.Equipment;
import com.campuslands.proyectocoworkhub.presentation.dto.EquipmentRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.EquipmentResponseDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
@Component
public class EquipmentMapper {
    public Equipment toEntity(EquipmentRequestDTO dto) {
        return new Equipment(dto.name());
    }

    public EquipmentResponseDTO toResponseDTO(Equipment equipment) {
        return new EquipmentResponseDTO(
                equipment.getId(),
                equipment.getName()
        );
    }
}
