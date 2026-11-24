package com.campuslands.proyectocoworkhub.presentation.controller;

import com.campuslands.proyectocoworkhub.busisness.service.RoomService;
import com.campuslands.proyectocoworkhub.persistence.entity.RoomType;
import com.campuslands.proyectocoworkhub.presentation.dto.RoomRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.RoomResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    // Registrar una sala
    @PostMapping
    public ResponseEntity<RoomResponseDTO> createRoom(
            @Valid @RequestBody RoomRequestDTO dto) {

        RoomResponseDTO responseDTO = roomService.createRoom(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO);
    }

    // Listar todas las salas
    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> getAllRooms() {

        return ResponseEntity.ok(roomService.getAllRooms());
    }

    // Buscar sala por ID
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> getRoomById(
            @PathVariable Long id) {

        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    // Buscar salas por sede
    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<RoomResponseDTO>> getRoomsByLocation(
            @PathVariable Long locationId) {

        return ResponseEntity.ok(
                roomService.getRoomsByLocation(locationId));
    }

    // Buscar salas por tipo
    @GetMapping("/type/{type}")
    public ResponseEntity<List<RoomResponseDTO>> getRoomsByType(
            @PathVariable RoomType type) {

        return ResponseEntity.ok(
                roomService.getRoomsByType(type));
    }

    // Buscar salas activas por sede
    @GetMapping("/active/{locationId}")
    public ResponseEntity<List<RoomResponseDTO>> getActiveRoomsByLocation(
            @PathVariable Long locationId) {

        return ResponseEntity.ok(
                roomService.getActiveRoomsByLocation(locationId));
    }

    // Actualizar sala
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomRequestDTO dto) {

        return ResponseEntity.ok(
                roomService.updateRoom(id, dto));
    }

    // Eliminar sala
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable Long id) {

        roomService.deleteRoom(id);

        return ResponseEntity.noContent().build();
    }
}
