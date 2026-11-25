package com.campuslands.proyectocoworkhub.busisness.service;

import com.campuslands.proyectocoworkhub.persistence.entity.Booking;
import com.campuslands.proyectocoworkhub.presentation.dto.AdditionalServiceRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.AdditionalServiceResponseDTO;

import java.util.List;

public interface AdditionalServiceService {
    // Registrar un servicio adicional
    AdditionalServiceResponseDTO createAdditionalService(
            AdditionalServiceRequestDTO dto
    );

    // Consultar un servicio adicional por su ID
    AdditionalServiceResponseDTO getAdditionalServiceById(Long id);

    // Listar todos los servicios adicionales
    List<AdditionalServiceResponseDTO> getAllAdditionalServices();

    // Consultar los servicios adicionales de una reserva
    List<AdditionalServiceResponseDTO> getAdditionalServicesByBooking(
            Long bookingId
    );

    // Actualizar un servicio adicional
    AdditionalServiceResponseDTO updateAdditionalService(
            Long id,
            AdditionalServiceRequestDTO dto
    );

    // Eliminar un servicio adicional
    void deleteAdditionalService(Long id);
}
