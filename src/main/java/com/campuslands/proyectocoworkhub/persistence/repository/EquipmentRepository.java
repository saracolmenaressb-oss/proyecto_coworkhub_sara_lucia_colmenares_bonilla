package com.campuslands.proyectocoworkhub.persistence.repository;

import com.campuslands.proyectocoworkhub.persistence.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    // Buscar por nombre
    Optional<Equipment> findByNameIgnoreCase(String name);

    // Comprobar si existe un equipamiento por nombre
    boolean existsByNameIgnoreCase(String name);

    // Comprobar si el equipamiento está asociado a alguna sala
    boolean existsByRooms_Id(Long roomId);
}
