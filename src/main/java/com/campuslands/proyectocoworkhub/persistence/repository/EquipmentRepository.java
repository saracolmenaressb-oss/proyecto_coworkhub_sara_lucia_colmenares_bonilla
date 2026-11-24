package com.campuslands.proyectocoworkhub.persistence.repository;

import com.campuslands.proyectocoworkhub.persistence.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    // Buscar por nombre ignorando mayúsculas y minúsculas
    Optional<Equipment> findByNameIgnoreCase(String name);

    // Comprobar si existe un equipamiento por nombre
    boolean existsByNameIgnoreCase(String name);

    // Comprobar si el equipamiento está asociado a alguna sala
    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM Room r
            JOIN r.equipments e
            WHERE e.id = :equipmentId
            """)
    boolean isEquipmentInUse(@Param("equipmentId") Long equipmentId);
}
