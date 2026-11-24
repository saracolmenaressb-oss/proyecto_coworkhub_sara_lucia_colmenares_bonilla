package com.campuslands.proyectocoworkhub.busisness.service;

import com.campuslands.proyectocoworkhub.presentation.dto.EquipmentRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.EquipmentResponseDTO;

import java.util.List;

public interface EquipmentService {
    // Registrar un equipamiento
    EquipmentResponseDTO createEquipment(EquipmentRequestDTO equipmentDTO);

    // Consultar un equipamiento por ID
    EquipmentResponseDTO getEquipmentById(Long id);

    // Listar todos los equipamientos
    List<EquipmentResponseDTO> getAllEquipment();

    // Buscar equipamiento por nombre
    EquipmentResponseDTO getEquipmentByName(String name);

    // Actualizar un equipamiento
    EquipmentResponseDTO updateEquipment(Long id, EquipmentRequestDTO equipmentDTO);

    // Eliminar un equipamiento
    void deleteEquipment(Long id);
}
