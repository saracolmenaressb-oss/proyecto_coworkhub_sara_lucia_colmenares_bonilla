package com.campuslands.proyectocoworkhub.busisness.service;

import com.campuslands.proyectocoworkhub.persistence.entity.Location;
import com.campuslands.proyectocoworkhub.presentation.dto.LocationRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.LocationResponseDTO;

import java.util.List;

public interface LocationService {
    LocationResponseDTO createLocation(LocationRequestDTO dto);

    LocationResponseDTO getLocationById(Long id);

    List<LocationResponseDTO> getAllLocations();

    List<LocationResponseDTO> getLocationsByCity(String city);

    LocationResponseDTO updateLocation(
            Long id,
            LocationRequestDTO dto
    );

    void deleteLocation(Long id);
}
