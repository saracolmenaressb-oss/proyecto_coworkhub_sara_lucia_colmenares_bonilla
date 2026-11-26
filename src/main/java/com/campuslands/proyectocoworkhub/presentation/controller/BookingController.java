package com.campuslands.proyectocoworkhub.presentation.controller;

import com.campuslands.proyectocoworkhub.busisness.service.BookingService;
import com.campuslands.proyectocoworkhub.persistence.entity.BookingStatus;
import com.campuslands.proyectocoworkhub.presentation.dto.BookingRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.BookingResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
//@PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST', 'MEMBER')")
@SecurityRequirement(name = "bearerAuth")
public class BookingController {

    private final BookingService bookingService;

    private String resolveUsername(Authentication authentication) {
        if (authentication != null && authentication.getName() != null && !authentication.getName().isBlank()) {
            return authentication.getName();
        }
        return "admin";
    }

    @Operation(
            summary = "Crear una reserva",
            description = """
                Crea una reserva para una sala.
                Acceso abierto en modo pruebas (sin restricción de rol).
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reserva creada correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Los datos de la reserva no cumplen las reglas de negocio"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la sala, miembro o servicio adicional"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "La sala ya tiene una reserva en ese horario"
            )
    })
    // RF-04: Crear reserva
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(
            @Valid @RequestBody BookingRequestDTO dto,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                bookingService.createBooking(dto, resolveUsername(authentication))
        );
    }

    @Operation(summary = "Consultar reserva por ID")
    // RF-05: Consultar reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                bookingService.getBookingById(id, resolveUsername(authentication))
        );
    }

    @Operation(summary = "Listar y filtrar reservas")
    // RF-05: Listar y filtrar reservas
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getBookings(
            @RequestParam(required = false) Long miembroId,
            @RequestParam(required = false) Long salaId,
            @RequestParam(required = false) LocalDate fecha,
            @RequestParam(required = false) BookingStatus estado,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                bookingService.getBookingsFiltered(
                        miembroId,
                        salaId,
                        fecha,
                        estado,
                        resolveUsername(authentication)
                )
        );
    }

    @Operation(summary = "Cancelar reserva")
    // RF-08: Cancelar reserva
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<BookingResponseDTO> cancelBooking(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                bookingService.cancelBooking(id, resolveUsername(authentication))
        );
    }
}
