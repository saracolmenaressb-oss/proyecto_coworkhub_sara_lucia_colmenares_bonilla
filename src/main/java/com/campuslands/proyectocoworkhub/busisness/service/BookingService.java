package com.campuslands.proyectocoworkhub.busisness.service;

import com.campuslands.proyectocoworkhub.persistence.entity.BookingStatus;
import com.campuslands.proyectocoworkhub.presentation.dto.BookingRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.BookingResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    BookingResponseDTO createBooking(
            BookingRequestDTO dto,
            String username
    );

    BookingResponseDTO getBookingById(
            Long id,
            String username
    );

    BookingResponseDTO cancelBooking(
            Long id,
            String username
    );

    // Listar todas las reservas
    List<BookingResponseDTO> getAllBookings();

    // Consultar reservas de un miembro
    List<BookingResponseDTO> getBookingsByMember(Long memberId);

    // Filtrar reservas por estado
    List<BookingResponseDTO> getBookingsByStatus(BookingStatus status);
    // Consultar reservas de una sala
    List<BookingResponseDTO> getBookingsByRoom(Long roomId);

    // Filtrar reservas por estado
    List<BookingResponseDTO> getBookingsFiltered(
            Long memberId,
            Long roomId,
            LocalDate date,
            BookingStatus status,
            String username
    );


}
