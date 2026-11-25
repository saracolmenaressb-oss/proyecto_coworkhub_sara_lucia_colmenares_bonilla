package com.campuslands.proyectocoworkhub.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;

public record BookingRequestDTO (
        @NotNull(message = "La fecha de inicio es obligatoria")
        @Future(message = "La fecha de inicio debe ser futura")
        LocalDateTime startTime,

        @NotNull(message = "La fecha de finalización es obligatoria")
        LocalDateTime endTime,

        @Positive(message = "La cantidad de asistentes debe ser mayor que cero")
        int attendees,

        @NotNull(message = "El identificador de la sala es obligatorio")
        @Positive(message = "El ID de la sala debe ser positivo")
        Long roomId,

        @NotNull(message = "El identificador del miembro es obligatorio")
        @Positive(message = "El ID del miembro debe ser positivo")
        Long memberId,

        @Valid
        List<BookingItemRequestDTO> items
){
}
