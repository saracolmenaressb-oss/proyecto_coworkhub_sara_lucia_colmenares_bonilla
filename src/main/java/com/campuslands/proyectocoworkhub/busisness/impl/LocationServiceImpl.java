package com.campuslands.proyectocoworkhub.busisness.impl;

import com.campuslands.proyectocoworkhub.busisness.service.LocationService;
import com.campuslands.proyectocoworkhub.persistence.entity.Location;
import com.campuslands.proyectocoworkhub.persistence.repository.LocationRepository;
import com.campuslands.proyectocoworkhub.persistence.repository.RoomRepository;
import com.campuslands.proyectocoworkhub.presentation.dto.LocationRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.LocationResponseDTO;
import com.campuslands.proyectocoworkhub.presentation.mapper.LocationMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final RoomRepository roomRepository;
    private final LocationMapper locationMapper;

    @Override
    public LocationResponseDTO createLocation(LocationRequestDTO dto) {

        validateSchedule(dto.getOpeningTime(), dto.getClosingTime());

        Location location = locationMapper.toEntity(dto);

        Location savedLocation = locationRepository.save(location);

        return locationMapper.toResponseDTO(savedLocation);
    }

    @Override
    public LocationResponseDTO getLocationById(Long id) {

        Location location = findLocation(id);

        return locationMapper.toResponseDTO(location);
    }

    @Override
    public List<LocationResponseDTO> getAllLocations() {

        return locationRepository.findAll()
                .stream()
                .map(locationMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<LocationResponseDTO> getLocationsByCity(String city) {

        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException(
                    "La ciudad es obligatoria"
            );
        }

        return locationRepository.findByCityIgnoreCase(city.trim())
                .stream()
                .map(locationMapper::toResponseDTO)
                .toList();
    }

    @Override
    public LocationResponseDTO updateLocation(
            Long id,
            LocationRequestDTO dto
    ) {

        Location existingLocation = findLocation(id);

        validateSchedule(dto.getOpeningTime(), dto.getClosingTime());

        existingLocation.setName(dto.getName());
        existingLocation.setCity(dto.getCity());
        existingLocation.setAddress(dto.getAddress());
        existingLocation.setOpeningTime(dto.getOpeningTime());
        existingLocation.setClosingTime(dto.getClosingTime());

        Location updatedLocation = locationRepository.save(existingLocation);

        return locationMapper.toResponseDTO(updatedLocation);
    }

    @Override
    public void deleteLocation(Long id) {

        Location location = locationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("La sede no existe")
                );

        // RN-12: verificar si la sede tiene salas
        boolean hasRooms = roomRepository.existsByLocation_Id(id);

        if (hasRooms) {
            throw new RuntimeException(
                    "No se puede eliminar la sede porque tiene salas asociadas"
            );
        }

        locationRepository.delete(location);
    }

    // Buscar sede y comprobar su existencia
    private Location findLocation(Long id) {

        return locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró la sede con ID: " + id
                ));
    }

    // Validar horario de apertura y cierre
    private void validateSchedule(
            LocalTime openingTime,
            LocalTime closingTime
    ) {

        if (openingTime == null || closingTime == null) {
            throw new IllegalArgumentException(
                    "El horario es obligatorio"
            );
        }

        if (!closingTime.isAfter(openingTime)) {
            throw new IllegalArgumentException(
                    "La hora de cierre debe ser posterior a la apertura"
            );
        }
    }
}
