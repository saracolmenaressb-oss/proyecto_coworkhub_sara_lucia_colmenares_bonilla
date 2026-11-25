package com.campuslands.proyectocoworkhub.busisness.impl;

import com.campuslands.proyectocoworkhub.busisness.service.BookingService;
import com.campuslands.proyectocoworkhub.persistence.entity.*;
import com.campuslands.proyectocoworkhub.persistence.repository.*;
import com.campuslands.proyectocoworkhub.presentation.dto.BookingItemRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.BookingRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.BookingResponseDTO;
import com.campuslands.proyectocoworkhub.presentation.mapper.BookingMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoockingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingItemRepository bookingItemRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final AdditionalServiceRepository additionalServiceRepository;
    private final BookingMapper bookingMapper;

    private static final List<BookingStatus> ACTIVE_STATUSES =
            List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED);

    // CREAR RESERVA
    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO dto) {

        LocalDateTime now = LocalDateTime.now();

        // 1. Buscar la sala
        Room room = roomRepository.findById(dto.roomId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "La sala no existe"
                ));

        // 2. Buscar el miembro
        Member member = memberRepository.findById(dto.memberId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "El miembro no existe"
                ));

        // 3. Validar estado del miembro
        if (member.getStatus() != MemberStatus.ACTIVE) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El miembro no está activo"
            );
        }

        // 4. Validar estado de la sala
        if (!room.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La sala no está activa"
            );
        }

        // 5. Validar capacidad
        if (dto.attendees() <= 0 ||
                dto.attendees() > room.getCapacity()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cantidad de asistentes supera la capacidad de la sala"
            );
        }

        // 6. Validar fechas y duración
        validateDuration(dto.startTime(), dto.endTime());

        // 7. Validar anticipación
        if (!dto.startTime().isAfter(now.plusHours(1))) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La reserva debe realizarse con mínimo una hora de anticipación"
            );
        }

        // 8. Validar horario de la sede
        validateLocationSchedule(room, dto.startTime(), dto.endTime());

        // 9. Validar límite de reservas activas
        int maxBookings = member.getMembershipPlan()
                .getMaxActiveBooking();

        long activeBookings =
                bookingRepository.countByMember_IdAndStatusInAndStartTimeAfter(
                        member.getId(),
                        ACTIVE_STATUSES,
                        now
                );

        if (activeBookings >= maxBookings) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El miembro alcanzó el límite de reservas activas de su plan"
            );
        }

        // 10. Comprobar disponibilidad
        List<Booking> overlapping =
                bookingRepository.findOverlappingBookings(
                        room.getId(),
                        ACTIVE_STATUSES,
                        dto.startTime(),
                        dto.endTime()
                );

        if (!overlapping.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La sala ya tiene una reserva en ese horario"
            );
        }

        // 11. Calcular costo de la sala
        long durationMinutes = Duration.between(
                dto.startTime(),
                dto.endTime()
        ).toMinutes();

        BigDecimal hours = BigDecimal.valueOf(durationMinutes)
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

        BigDecimal roomCost = room.getHourlyRate()
                .multiply(hours)
                .setScale(2, RoundingMode.HALF_UP);

        // 12. Crear reserva
        Booking booking = new Booking(
                dto.startTime(),
                dto.endTime(),
                dto.attendees(),
                BookingStatus.PENDING,
                roomCost,
                BigDecimal.ZERO,
                roomCost,
                BigDecimal.ZERO,
                room,
                member
        );

        // 13. Registrar servicios adicionales
        BigDecimal serviceCost = BigDecimal.ZERO;

        if (dto.items() != null) {

            for (BookingItemRequestDTO itemDTO : dto.items()) {

                if (itemDTO.quantity() <= 0) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "La cantidad del servicio debe ser mayor que cero"
                    );
                }

                AdditionalService additionalService =
                        additionalServiceRepository.findById(
                                itemDTO.additionalServiceId()
                        ).orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "El servicio adicional no existe"
                        ));

                BigDecimal appliedPrice = additionalService.getUnitPrice();

                BigDecimal subtotal = appliedPrice.multiply(
                        BigDecimal.valueOf(itemDTO.quantity())
                );

                BookingItem item = new BookingItem(
                        itemDTO.quantity(),
                        appliedPrice,
                        additionalService
                );

                booking.addItem(item);

                serviceCost = serviceCost.add(subtotal);
            }
        }

        // 14. Calcular costo total
        BigDecimal totalCost = roomCost.add(serviceCost);

        booking.setServiceCost(serviceCost);
        booking.setTotalCost(totalCost);

        // 15. Guardar reserva
        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapper.toResponseDTO(savedBooking);
    }

    // VALIDAR DURACIÓN
    private void validateDuration(
            LocalDateTime startTime,
            LocalDateTime endTime) {

        if (startTime == null || endTime == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Las fechas son obligatorias"
            );
        }

        if (!endTime.isAfter(startTime)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La fecha final debe ser posterior a la inicial"
            );
        }

        if (!startTime.toLocalDate().equals(endTime.toLocalDate())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La reserva debe realizarse en un mismo día"
            );
        }

        long minutes = Duration.between(startTime, endTime).toMinutes();

        if (minutes < 30 || minutes > 480 || minutes % 30 != 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La duración debe estar entre 30 minutos y 8 horas, en múltiplos de 30 minutos"
            );
        }
    }

    // VALIDAR HORARIO DE LA SEDE
    private void validateLocationSchedule(
            Room room,
            LocalDateTime startTime,
            LocalDateTime endTime) {

        Location location = room.getLocation();

        LocalTime opening = location.getOpeningTime();
        LocalTime closing = location.getClosingTime();

        if (startTime.toLocalTime().isBefore(opening) ||
                endTime.toLocalTime().isAfter(closing)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La reserva está fuera del horario de funcionamiento de la sede"
            );
        }
    }

    // CONSULTAR POR ID
    @Override
    @Transactional(readOnly = true)
    public BookingResponseDTO getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Reserva no encontrada"
                ));

        return bookingMapper.toResponseDTO(booking);
    }

    // LISTAR TODAS
    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDTO> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(bookingMapper::toResponseDTO)
                .toList();
    }

    // BUSCAR POR MIEMBRO
    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDTO> getBookingsByMember(Long memberId) {

        if (!memberRepository.existsById(memberId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Miembro no encontrado"
            );
        }

        return bookingRepository.findByMember_Id(memberId)
                .stream()
                .map(bookingMapper::toResponseDTO)
                .toList();
    }

    // BUSCAR POR SALA
    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDTO> getBookingsByRoom(Long roomId) {

        if (!roomRepository.existsById(roomId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Sala no encontrada"
            );
        }

        return bookingRepository.findByRoom_Id(roomId)
                .stream()
                .map(bookingMapper::toResponseDTO)
                .toList();
    }

    // BUSCAR POR ESTADO
    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDTO> getBookingsByStatus(
            BookingStatus status) {

        return bookingRepository.findByStatus(status)
                .stream()
                .map(bookingMapper::toResponseDTO)
                .toList();
    }

    // CANCELAR RESERVA
    @Override
    public BookingResponseDTO cancelBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Reserva no encontrada"
                ));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La reserva ya está cancelada"
            );
        }

        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede cancelar una reserva completada"
            );
        }

        LocalDateTime now = LocalDateTime.now();

        if (!booking.getStartTime().isAfter(now)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede cancelar una reserva que ya comenzó"
            );
        }

        long minutesUntilStart = Duration.between(
                now,
                booking.getStartTime()
        ).toMinutes();

        if (minutesUntilStart < 120) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede cancelar con menos de dos horas de anticipación"
            );
        }

        BigDecimal cancellationFee;

        if (minutesUntilStart >= 1440) {

            cancellationFee = BigDecimal.ZERO;
            booking.setHoursReturned(true);

        } else {

            cancellationFee = booking.getRoomCost()
                    .multiply(new BigDecimal("0.50"))
                    .setScale(2, RoundingMode.HALF_UP);

            booking.setHoursReturned(false);
        }

        booking.setCancellationFee(cancellationFee);
        booking.setStatus(BookingStatus.CANCELLED);

        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapper.toResponseDTO(savedBooking);
    }
}
