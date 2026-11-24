package com.campuslands.proyectocoworkhub.presentation.mapper;

import com.campuslands.proyectocoworkhub.persistence.entity.Room;
import com.campuslands.proyectocoworkhub.presentation.dto.EquipmentResponseDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.LocationSummaryDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.RoomRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.RoomResponseDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class RoomMapper {
    public Room toEntity(RoomRequestDTO dto) {

        return new Room(
                dto.name(),
                dto.type(),
                dto.capacity(),
                dto.hourlyRate(),
                true
        );
    }
    public RoomResponseDTO toResponseDTO(Room room) {

        return new RoomResponseDTO(
                room.getId(),
                room.getName(),
                room.getType(),
                room.getCapacity(),
                room.getHourlyRate(),
                room.isActive(),
                new LocationSummaryDTO(
                        room.getLocation().getId(),
                        room.getLocation().getName()
                ),
                room.getEquipments().stream()
                        .map(equipment -> new EquipmentResponseDTO(
                                equipment.getId(),
                                equipment.getName()
                        ))
                        .collect(Collectors.toSet())
        );
    }
}
