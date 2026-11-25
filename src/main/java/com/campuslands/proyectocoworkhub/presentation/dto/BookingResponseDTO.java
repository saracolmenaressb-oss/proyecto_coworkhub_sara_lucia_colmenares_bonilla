package com.campuslands.proyectocoworkhub.presentation.dto;

import com.campuslands.proyectocoworkhub.persistence.entity.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BookingResponseDTO (
        Long id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        int attendees,
        BookingStatus status,
        BigDecimal roomCost,
        BigDecimal serviceCost,
        BigDecimal totalCost,
        BigDecimal cancellationFee,
        boolean hoursReturned,
        Long roomId,
        String roomName,
        Long memberId,
        String memberName,
        List<BookingItemResponseDTO> items
){
}
