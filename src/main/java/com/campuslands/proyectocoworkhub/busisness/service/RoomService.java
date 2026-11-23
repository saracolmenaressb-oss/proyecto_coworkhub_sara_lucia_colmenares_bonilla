package com.campuslands.proyectocoworkhub.busisness.service;

import com.campuslands.proyectocoworkhub.persistence.entity.Room;
import com.campuslands.proyectocoworkhub.persistence.entity.RoomType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface RoomService {
    // Registrar una sala
    Room createRoom(Room room);

    // Buscar una sala por ID
    Room getRoomById(Long id);

    // Listar todas las salas
    List<Room> getAllRooms();

    // Listar las salas de una sede
    List<Room> getRoomsByLocation(Long locationId);

    // Buscar salas por tipo
    List<Room> getRoomsByType(RoomType type);

    // Listar las salas activas de una sede
    List<Room> getActiveRoomsByLocation(Long locationId);

    // Actualizar una sala
    Room updateRoom(Long id, Room room);

    // Eliminar una sala
    void deleteRoom(Long id);

    // Buscar salas disponibles según fecha, horario y filtros
    List<Room> getAvailableRooms(
            Long locationId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            Integer minCapacity,
            Collection<Long> equipmentIds
    );
}
