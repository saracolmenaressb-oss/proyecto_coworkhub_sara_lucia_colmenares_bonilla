package com.campuslands.proyectocoworkhub.presentation.controller;

import com.campuslands.proyectocoworkhub.busisness.service.AdditionalServiceService;
import com.campuslands.proyectocoworkhub.presentation.dto.AdditionalServiceRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.AdditionalServiceResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/additional-services")
@RequiredArgsConstructor
@Validated
public class AdditionalServiceController {
    private final AdditionalServiceService additionalServiceService;

    //Registrar un servicio adicional
    @PostMapping
    public ResponseEntity<AdditionalServiceResponseDTO> createAdditionalService(
            @Valid @RequestBody AdditionalServiceRequestDTO dto
            ){
        AdditionalServiceResponseDTO response =
                additionalServiceService.createAdditionalService(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    // Consultar un servicio adicional por ID
    @GetMapping("/{id}")
    public ResponseEntity<AdditionalServiceResponseDTO> getAdditionalServiceById(
            @PathVariable @Positive Long id) {

        return ResponseEntity.ok(
                additionalServiceService.getAdditionalServiceById(id)
        );
    }

    // Listar todos los servicios adicionales
    @GetMapping
    public ResponseEntity<List<AdditionalServiceResponseDTO>> getAllAdditionalServices() {

        return ResponseEntity.ok(
                additionalServiceService.getAllAdditionalServices()
        );
    }

    // Consultar servicios adicionales asociados a una reserva
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<AdditionalServiceResponseDTO>> getAdditionalServicesByBooking(
            @PathVariable @Positive Long bookingId) {

        return ResponseEntity.ok(
                additionalServiceService.getAdditionalServicesByBooking(bookingId)
        );
    }

    // Actualizar un servicio adicional
    @PutMapping("/{id}")
    public ResponseEntity<AdditionalServiceResponseDTO> updateAdditionalService(
            @PathVariable @Positive Long id,
            @Valid @RequestBody AdditionalServiceRequestDTO dto) {

        return ResponseEntity.ok(
                additionalServiceService.updateAdditionalService(id, dto)
        );
    }

    // Eliminar un servicio adicional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdditionalService(
            @PathVariable @Positive Long id) {

        additionalServiceService.deleteAdditionalService(id);

        return ResponseEntity.noContent().build();
    }
}
