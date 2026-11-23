package com.campuslands.proyectocoworkhub.presentation.mapper;

import com.campuslands.proyectocoworkhub.persistence.entity.Location;
import com.campuslands.proyectocoworkhub.presentation.dto.LocationRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.LocationResponseDTO;

public class LocationMapper {
    // Convertir RequestDTO en Entity
    public Location toEntity(LocationRequestDTO dto) {

        return new Location(
                dto.getName(),
                dto.getCity(),
                dto.getAddress(),
                dto.getOpeningTime(),
                dto.getClosingTime()
        );
    }

    // Convertir Entity en ResponseDTO
    public LocationResponseDTO toResponseDTO(Location location) {

        return new LocationResponseDTO(
                location.getId(),
                location.getName(),
                location.getCity(),
                location.getAddress(),
                location.getOpeningTime(),
                location.getClosingTime()
        );
    }
}
