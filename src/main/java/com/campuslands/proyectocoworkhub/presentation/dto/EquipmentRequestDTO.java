package com.campuslands.proyectocoworkhub.presentation.dto;

import com.campuslands.proyectocoworkhub.persistence.entity.Room;

import java.util.Set;

public record EquipmentRequestDTO (
    String name,
    Set<Room> rooms
){
}
