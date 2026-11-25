package com.campuslands.proyectocoworkhub.presentation.mapper;

import com.campuslands.proyectocoworkhub.persistence.entity.AdditionalService;
import com.campuslands.proyectocoworkhub.presentation.dto.AdditionalServiceRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.AdditionalServiceResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AdditionalServiceMapper {
    // Convierte el DTO de entrada en una entidad
    public AdditionalService toEntity(AdditionalServiceRequestDTO dto) {

        return new AdditionalService(
                dto.name().trim(),
                dto.unitPrice()
        );
    }
    // Convierte una entidad en un DTO de respuesta
    public AdditionalServiceResponseDTO toResponseDTO(
            AdditionalService additionalService) {

        return new AdditionalServiceResponseDTO(
                additionalService.getId(),
                additionalService.getName(),
                additionalService.getUnitPrice()
        );
    }
}
