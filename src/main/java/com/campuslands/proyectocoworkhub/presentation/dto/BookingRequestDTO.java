package com.campuslands.proyectocoworkhub.presentation.dto;

import java.time.LocalDateTime;
import java.util.List;

public record BookingRequestDTO (
        LocalDateTime startTime,
        LocalDateTime endTime,
        int attendees,
        Long roomId,
        Long memberId,
        List<BookingItemRequestDTO> items
){
}
