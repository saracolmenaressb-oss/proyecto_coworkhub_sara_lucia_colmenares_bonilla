package com.campuslands.proyectocoworkhub.presentation.controller;

import com.campuslands.proyectocoworkhub.busisness.service.BookingService;
import com.campuslands.proyectocoworkhub.persistence.entity.BookingStatus;
import com.campuslands.proyectocoworkhub.presentation.dto.BookingRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.BookingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // Crear reserva
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(
            @RequestBody BookingRequestDTO dto) {

        BookingResponseDTO response =
                bookingService.createBooking(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // Consultar reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.getBookingById(id)
        );
    }

    // Listar reservas
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {

        return ResponseEntity.ok(
                bookingService.getAllBookings()
        );
    }

    // Consultar reservas de un miembro
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByMember(
            @PathVariable Long memberId) {

        return ResponseEntity.ok(
                bookingService.getBookingsByMember(memberId)
        );
    }

    // Consultar reservas de una sala
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByRoom(
            @PathVariable Long roomId) {

        return ResponseEntity.ok(
                bookingService.getBookingsByRoom(roomId)
        );
    }

    // Filtrar por estado
    @GetMapping("/status/{status}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByStatus(
            @PathVariable BookingStatus status) {

        return ResponseEntity.ok(
                bookingService.getBookingsByStatus(status)
        );
    }

    // Cancelar reserva
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.cancelBooking(id)
        );
    }
}
