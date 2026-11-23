package com.campuslands.proyectocoworkhub.presentation.controller;

import com.campuslands.proyectocoworkhub.busisness.service.LocationService;
import com.campuslands.proyectocoworkhub.presentation.dto.LocationRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.LocationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    // Registrar una sede
    @PostMapping
    public ResponseEntity<LocationResponseDTO> createLocation(
            @Valid @RequestBody LocationRequestDTO dto) {

        LocationResponseDTO response =
                locationService.createLocation(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Obtener todas las sedes
    @GetMapping
    public ResponseEntity<List<LocationResponseDTO>> getAllLocations() {

        return ResponseEntity.ok(locationService.getAllLocations());
    }

    // Buscar una sede por ID
    @GetMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> getLocationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(locationService.getLocationById(id));
    }

    // Buscar sedes por ciudad
    @GetMapping("/city/{city}")
    public ResponseEntity<List<LocationResponseDTO>> getLocationsByCity(
            @PathVariable String city) {

        return ResponseEntity.ok(locationService.getLocationsByCity(city));
    }

    // Actualizar una sede
    @PutMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> updateLocation(
            @PathVariable Long id,
            @Valid @RequestBody LocationRequestDTO dto) {

        return ResponseEntity.ok(
                locationService.updateLocation(id, dto)
        );
    }

    // Eliminar una sede
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(
            @PathVariable Long id) {

        locationService.deleteLocation(id);

        return ResponseEntity.noContent().build();
    }
}
