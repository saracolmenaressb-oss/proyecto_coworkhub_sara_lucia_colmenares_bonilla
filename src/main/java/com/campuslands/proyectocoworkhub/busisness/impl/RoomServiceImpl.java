package com.campuslands.proyectocoworkhub.busisness.impl;

import com.campuslands.proyectocoworkhub.busisness.service.RoomService;
import com.campuslands.proyectocoworkhub.persistence.entity.*;
import com.campuslands.proyectocoworkhub.persistence.repository.BookingRepository;
import com.campuslands.proyectocoworkhub.persistence.repository.EquipmentRepository;
import com.campuslands.proyectocoworkhub.persistence.repository.LocationRepository;
import com.campuslands.proyectocoworkhub.persistence.repository.RoomRepository;
import com.campuslands.proyectocoworkhub.presentation.dto.RoomRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.RoomResponseDTO;
import com.campuslands.proyectocoworkhub.presentation.exception.ResourceInUseException;
import com.campuslands.proyectocoworkhub.presentation.mapper.RoomMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final LocationRepository locationRepository;
    private final RoomRepository roomRepository;
    private final EquipmentRepository equipmentRepository;
    private final RoomMapper roomMapper;
    private final BookingRepository bookingRepository;
    // 1. Registrar una sala
    @Override
    @Transactional
    public RoomResponseDTO createRoom(RoomRequestDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException(
                    "La información de la sala es obligatoria"
            );
        }

        validateLocationId(dto.locationId());

        Location location = findLocationById(dto.locationId());

        Room room = roomMapper.toEntity(dto);

        validateRoom(room);

        boolean nameExists =
                roomRepository.existsByNameIgnoreCaseAndLocation_Id(
                        room.getName().trim(),
                        location.getId()
                );

        if (nameExists) {
            throw new IllegalStateException(
                    "Ya existe una sala con ese nombre en la sede"
            );
        }

        room.setName(room.getName().trim());
        room.setLocation(location);

        assignEquipments(room, dto.equipmentIds());

        Room savedRoom = roomRepository.save(room);

        return roomMapper.toResponseDTO(savedRoom);
    }

    // 2. Buscar sala por ID
    @Override
    @Transactional(readOnly = true)
    public RoomResponseDTO getRoomById(Long id) {

        validateId(id);

        Room room = findRoomById(id);

        return roomMapper.toResponseDTO(room);
    }

    // 3. Listar todas las salas
    @Override
    @Transactional(readOnly = true)
    public List<RoomResponseDTO> getAllRooms() {

        return roomRepository.findAll()
                .stream()
                .map(roomMapper::toResponseDTO)
                .toList();
    }

    // 4. Buscar salas por sede
    @Override
    @Transactional(readOnly = true)
    public List<RoomResponseDTO> getRoomsByLocation(Long locationId) {

        validateId(locationId);
        findLocationById(locationId);

        return roomRepository.findByLocation_Id(locationId)
                .stream()
                .map(roomMapper::toResponseDTO)
                .toList();
    }

    // 5. Buscar salas por tipo
    @Override
    @Transactional(readOnly = true)
    public List<RoomResponseDTO> getRoomsByType(RoomType type) {

        if (type == null) {
            throw new IllegalArgumentException(
                    "El tipo de sala es obligatorio"
            );
        }

        return roomRepository.findByType(type)
                .stream()
                .map(roomMapper::toResponseDTO)
                .toList();
    }

    // 6. Buscar salas activas por sede
    @Override
    @Transactional(readOnly = true)
    public List<RoomResponseDTO> getActiveRoomsByLocation(
            Long locationId) {

        validateId(locationId);
        findLocationById(locationId);

        return roomRepository
                .findByLocation_IdAndActiveTrue(locationId)
                .stream()
                .map(roomMapper::toResponseDTO)
                .toList();
    }

    // 7. Actualizar una sala
    @Override
    @Transactional
    public RoomResponseDTO updateRoom(Long id, RoomRequestDTO dto) {

        validateId(id);

        if (dto == null) {
            throw new IllegalArgumentException(
                    "La información de la sala es obligatoria"
            );
        }

        Room existingRoom = findRoomById(id);

        validateLocationId(dto.locationId());

        Location location = findLocationById(dto.locationId());

        Room roomData = roomMapper.toEntity(dto);

        validateRoom(roomData);

        boolean nameExists =
                roomRepository
                        .existsByNameIgnoreCaseAndLocation_IdAndIdNot(
                                roomData.getName().trim(),
                                location.getId(),
                                id
                        );

        if (nameExists) {
            throw new IllegalStateException(
                    "Ya existe otra sala con ese nombre en la sede"
            );
        }

        existingRoom.setName(roomData.getName().trim());
        existingRoom.setType(roomData.getType());
        existingRoom.setCapacity(roomData.getCapacity());
        existingRoom.setHourlyRate(roomData.getHourlyRate());

        existingRoom.setLocation(location);

        assignEquipments(existingRoom, dto.equipmentIds());

        Room updatedRoom = roomRepository.save(existingRoom);

        return roomMapper.toResponseDTO(updatedRoom);
    }

    // 8. Eliminar una sala
    @Override
    @Transactional
    public void deleteRoom(Long id) {

        validateId(id);

        Room room = roomRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("La sala no existe")
                );
        boolean hasBookings =
                roomRepository.existsByBookings_Id(id);

        if (hasBookings) {
            throw new ResourceInUseException(
                    "No se puede eliminar la sala porque tiene reservas asociadas"
            );
        }

        roomRepository.delete(room);
    }

    // 9. Consultar salas disponibles
    @Override
    @Transactional(readOnly = true)
    public List<RoomResponseDTO> getAvailableRooms(
            Long locationId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            Integer minCapacity,
            Collection<Long> equipmentIds) {

        validateId(locationId);
        findLocationById(locationId);

        if (date == null || startTime == null || endTime == null) {
            throw new IllegalArgumentException(
                    "La fecha y los horarios son obligatorios"
            );
        }

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException(
                    "La hora de finalización debe ser posterior al inicio"
            );
        }

        if (minCapacity != null && minCapacity <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad mínima debe ser mayor que cero"
            );
        }

        LocalDateTime startDateTime =
                LocalDateTime.of(date, startTime);

        LocalDateTime endDateTime =
                LocalDateTime.of(date, endTime);

        if (startDateTime.isBefore(
                LocalDateTime.now().plusHours(1))) {

            throw new IllegalArgumentException(
                    "La búsqueda debe realizarse con al menos una hora de anticipación"
            );
        }

        List<BookingStatus> blockingStatuses = List.of(
                BookingStatus.PENDING,
                BookingStatus.CONFIRMED
        );

        List<Room> availableRooms =
                roomRepository.findAvailableRooms(
                        locationId,
                        startDateTime,
                        endDateTime,
                        blockingStatuses,
                        minCapacity
                );

        if (equipmentIds != null && !equipmentIds.isEmpty()) {

            Set<Long> requiredEquipmentIds =
                    new HashSet<>(equipmentIds);

            availableRooms = availableRooms.stream()
                    .filter(room -> {

                        Set<Long> roomEquipmentIds =
                                room.getEquipments().stream()
                                        .map(Equipment::getId)
                                        .collect(Collectors.toSet());

                        return roomEquipmentIds.containsAll(
                                requiredEquipmentIds
                        );
                    })
                    .toList();
        }

        return availableRooms.stream()
                .map(roomMapper::toResponseDTO)
                .toList();
    }

    // MÉTODOS AUXILIARES

    // Validar información de la sala
    private void validateRoom(Room room) {

        if (room.getName() == null ||
                room.getName().isBlank()) {

            throw new IllegalArgumentException(
                    "El nombre de la sala es obligatorio"
            );
        }

        if (room.getCapacity() <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que cero"
            );
        }

        if (room.getHourlyRate() == null ||
                room.getHourlyRate().signum() < 0) {

            throw new IllegalArgumentException(
                    "La tarifa por hora no puede ser negativa"
            );
        }

        if (room.getType() == null) {
            throw new IllegalArgumentException(
                    "El tipo de sala es obligatorio"
            );
        }
    }

    // Validar identificadores
    private void validateId(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser un número positivo"
            );
        }
    }

    // Validar ID de sede
    private void validateLocationId(Long locationId) {
        validateId(locationId);
    }

    // Buscar una sede
    private Location findLocationById(Long id) {

        return locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró la sede con ID " + id
                ));
    }

    // Buscar una sala
    private Room findRoomById(Long id) {

        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró la sala con ID " + id
                ));
    }

    // Asignar equipos a una sala
    private void assignEquipments(
            Room room,
            Collection<Long> equipmentIds) {

        // Eliminar las relaciones anteriores
        for (Equipment equipment :
                new HashSet<>(room.getEquipments())) {

            room.removeEquipment(equipment);
        }

        if (equipmentIds == null || equipmentIds.isEmpty()) {
            return;
        }

        Set<Long> requiredIds = new HashSet<>(equipmentIds);

        List<Equipment> equipments =
                equipmentRepository.findAllById(requiredIds);

        if (equipments.size() != requiredIds.size()) {
            throw new EntityNotFoundException(
                    "Uno o varios equipos indicados no existen"
            );
        }

        for (Equipment equipment : equipments) {
            room.addEquipment(equipment);
        }
    }
}
