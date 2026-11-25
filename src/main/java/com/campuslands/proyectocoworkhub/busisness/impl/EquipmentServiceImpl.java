package com.campuslands.proyectocoworkhub.busisness.impl;

import com.campuslands.proyectocoworkhub.busisness.service.EquipmentService;
import com.campuslands.proyectocoworkhub.persistence.entity.Equipment;
import com.campuslands.proyectocoworkhub.persistence.repository.EquipmentRepository;
import com.campuslands.proyectocoworkhub.persistence.repository.RoomRepository;
import com.campuslands.proyectocoworkhub.presentation.dto.EquipmentRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.EquipmentResponseDTO;
import com.campuslands.proyectocoworkhub.presentation.mapper.EquipmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final EquipmentMapper equipmentMapper;
    private final RoomRepository roomRepository;
    @Override
    public EquipmentResponseDTO createEquipment(EquipmentRequestDTO equipmentDTO) {
        String name = equipmentDTO.name().trim();

        // Validar que no exista otro equipamiento con el mismo nombre
        if (equipmentRepository.findByNameIgnoreCase(name).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un equipamiento con ese nombre"
            );
        }

        // Convertir DTO en entidad
        Equipment equipment = equipmentMapper.toEntity(
                new EquipmentRequestDTO(name)
        );

        // Guardar en la base de datos
        Equipment savedEquipment = equipmentRepository.save(equipment);

        // Convertir entidad guardada en DTO de respuesta
        return equipmentMapper.toResponseDTO(savedEquipment);
    }

    @Override
    public EquipmentResponseDTO getEquipmentById(Long id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró el equipamiento con ID: " + id
                ));

        return equipmentMapper.toResponseDTO(equipment);
    }

    @Override
    public List<EquipmentResponseDTO> getAllEquipment() {
        return equipmentRepository.findAll()
                .stream()
                .map(equipmentMapper::toResponseDTO)
                .toList();
    }

    @Override
    public EquipmentResponseDTO getEquipmentByName(String name) {
        Equipment equipment = equipmentRepository
                .findByNameIgnoreCase(name.trim())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró el equipamiento: " + name
                ));

        return equipmentMapper.toResponseDTO(equipment);
    }

    @Override
    public EquipmentResponseDTO updateEquipment(Long id, EquipmentRequestDTO equipmentDTO) {
        // Verificar que el equipamiento exista
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró el equipamiento con ID: " + id
                ));

        String name = equipmentDTO.name().trim();

        // Verificar que el nuevo nombre no pertenezca a otro equipamiento
        Optional<Equipment> existingEquipment =
                equipmentRepository.findByNameIgnoreCase(name);

        if (existingEquipment.isPresent()
                && !existingEquipment.get().getId().equals(id)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe otro equipamiento con ese nombre"
            );
        }

        // Actualizar el nombre de la entidad existente
        equipment.setName(name);

        Equipment updatedEquipment = equipmentRepository.save(equipment);

        return equipmentMapper.toResponseDTO(updatedEquipment);
    }

    @Override
    public void deleteEquipment(Long id) {
        // Verificar que el equipamiento exista
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró el equipamiento con ID: " + id
                ));

        // Verificar si está asociado a alguna sala
        if (equipment.getRooms() != null && !equipment.getRooms().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el equipamiento porque está asociado a una sala"
            );
        }

        equipmentRepository.delete(equipment);

    }
}
