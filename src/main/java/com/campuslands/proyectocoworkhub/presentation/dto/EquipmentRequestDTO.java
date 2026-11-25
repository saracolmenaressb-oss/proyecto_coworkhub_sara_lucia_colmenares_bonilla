package com.campuslands.proyectocoworkhub.presentation.dto;

import com.campuslands.proyectocoworkhub.persistence.entity.Room;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record EquipmentRequestDTO (
        @NotBlank(message = "El nombre del equipamiento es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String name
){
}
