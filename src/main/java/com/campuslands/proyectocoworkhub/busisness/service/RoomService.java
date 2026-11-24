package com.campuslands.proyectocoworkhub.busisness.service;

import com.campuslands.proyectocoworkhub.persistence.entity.Room;
import com.campuslands.proyectocoworkhub.persistence.entity.RoomType;
import com.campuslands.proyectocoworkhub.presentation.dto.RoomRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.RoomResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface RoomService {

    RoomResponseDTO createRoom(RoomRequestDTO dto);

    List<RoomResponseDTO> getAllRooms();

    RoomResponseDTO getRoomById(Long id);

    List<RoomResponseDTO> getRoomsByLocation(Long locationId);

    List<RoomResponseDTO> getRoomsByType(RoomType type);

    List<RoomResponseDTO> getActiveRoomsByLocation(Long locationId);

    RoomResponseDTO updateRoom(Long id, RoomRequestDTO dto);

    void deleteRoom(Long id);

    List<RoomResponseDTO> getAvailableRooms(
            Long locationId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            Integer minCapacity,
            Collection<Long> equipmentIds
    );
}
