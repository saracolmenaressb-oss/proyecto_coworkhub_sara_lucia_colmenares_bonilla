package com.campuslands.proyectocoworkhub.presentation.controller;

import com.campuslands.proyectocoworkhub.busisness.service.BookingService;
import com.campuslands.proyectocoworkhub.persistence.entity.BookingStatus;
import com.campuslands.proyectocoworkhub.presentation.dto.BookingRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.BookingResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // RF-04: Crear reserva
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(
            @Valid @RequestBody BookingRequestDTO dto) {

        BookingResponseDTO response =
                bookingService.createBooking(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // RF-05: Consultar reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.getBookingById(id)
        );
    }

    // RF-05: Listar y filtrar reservas
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getBookings(
            @RequestParam(required = false) Long miembroId,
            @RequestParam(required = false) Long salaId,
            @RequestParam(required = false) LocalDate fecha,
            @RequestParam(required = false) BookingStatus estado) {

        return ResponseEntity.ok(
                bookingService.getBookingsFiltered(
                        miembroId,
                        salaId,
                        fecha,
                        estado
                )
        );
    }

    // RF-08: Cancelar reserva
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<BookingResponseDTO> cancelBooking(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.cancelBooking(id)
        );
    }
}
