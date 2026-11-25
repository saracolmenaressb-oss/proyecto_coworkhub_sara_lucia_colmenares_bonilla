package com.campuslands.proyectocoworkhub.busisness.service;

import com.campuslands.proyectocoworkhub.persistence.entity.BookingStatus;
import com.campuslands.proyectocoworkhub.presentation.dto.BookingRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.BookingResponseDTO;

import java.util.List;

public interface BookingService {
    // Crear una reserva
    BookingResponseDTO createBooking(BookingRequestDTO dto);

    // Consultar una reserva por ID
    BookingResponseDTO getBookingById(Long id);

    // Listar todas las reservas
    List<BookingResponseDTO> getAllBookings();

    // Consultar reservas de un miembro
    List<BookingResponseDTO> getBookingsByMember(Long memberId);

    // Consultar reservas de una sala
    List<BookingResponseDTO> getBookingsByRoom(Long roomId);

    // Filtrar reservas por estado
    List<BookingResponseDTO> getBookingsByStatus(BookingStatus status);

    // Cancelar una reserva
    BookingResponseDTO cancelBooking(Long id);
}
