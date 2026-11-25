package com.campuslands.proyectocoworkhub.presentation.mapper;

import com.campuslands.proyectocoworkhub.persistence.entity.Booking;
import com.campuslands.proyectocoworkhub.persistence.entity.BookingItem;
import com.campuslands.proyectocoworkhub.presentation.dto.BookingItemResponseDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.BookingResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class BookingMapper {
    public BookingItemResponseDTO toItemResponseDTO(BookingItem item) {

        BigDecimal subtotal = item.getAppliedUnitPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));

        return new BookingItemResponseDTO(
                item.getId(),
                item.getAdditionalService().getId(),
                item.getAdditionalService().getName(),
                item.getQuantity(),
                item.getAppliedUnitPrice(),
                subtotal
        );
    }

    public BookingResponseDTO toResponseDTO(Booking booking) {

        List<BookingItemResponseDTO> items = booking.getItems()
                .stream()
                .map(this::toItemResponseDTO)
                .toList();

        return new BookingResponseDTO(
                booking.getId(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getAttendees(),
                booking.getStatus(),
                booking.getRoomCost(),
                booking.getServiceCost(),
                booking.getTotalCost(),
                booking.getCancellationFee(),
                booking.isHoursReturned(),
                booking.getRoom().getId(),
                booking.getRoom().getName(),
                booking.getMember().getId(),
                booking.getMember().getFullName(),
                items
        );
    }
}
