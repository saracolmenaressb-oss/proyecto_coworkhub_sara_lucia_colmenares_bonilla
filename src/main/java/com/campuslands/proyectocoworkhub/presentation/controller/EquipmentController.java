package com.campuslands.proyectocoworkhub.presentation.controller;

import com.campuslands.proyectocoworkhub.busisness.service.EquipmentService;
import com.campuslands.proyectocoworkhub.presentation.dto.EquipmentRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.EquipmentResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipments")
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;

    public ResponseEntity<EquipmentResponseDTO> createEquipment(
            @Valid @RequestBody EquipmentRequestDTO equipmentDTO
    ) {
        EquipmentResponseDTO response =
                equipmentService.createEquipment(equipmentDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. Listar todos los equipamientos
    @GetMapping
    public ResponseEntity<List<EquipmentResponseDTO>> getAllEquipment() {

        return ResponseEntity.ok(
                equipmentService.getAllEquipment()
        );
    }

    // 3. Buscar equipamiento por ID
    @GetMapping("/{id}")
    public ResponseEntity<EquipmentResponseDTO> getEquipmentById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                equipmentService.getEquipmentById(id)
        );
    }

    // 4. Buscar equipamiento por nombre
    @GetMapping("/search")
    public ResponseEntity<EquipmentResponseDTO> getEquipmentByName(
            @RequestParam String name
    ) {

        return ResponseEntity.ok(
                equipmentService.getEquipmentByName(name)
        );
    }

    // 5. Actualizar equipamiento
    @PutMapping("/{id}")
    public ResponseEntity<EquipmentResponseDTO> updateEquipment(
            @PathVariable Long id,
            @Valid @RequestBody EquipmentRequestDTO equipmentDTO
    ) {

        return ResponseEntity.ok(
                equipmentService.updateEquipment(id, equipmentDTO)
        );
    }

    // 6. Eliminar equipamiento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(
            @PathVariable Long id
    ) {

        equipmentService.deleteEquipment(id);

        return ResponseEntity.noContent().build();
    }
}
