package com.campuslands.proyectocoworkhub.persistence.repository;

import com.campuslands.proyectocoworkhub.persistence.entity.BookingStatus;
import com.campuslands.proyectocoworkhub.persistence.entity.Room;
import com.campuslands.proyectocoworkhub.persistence.entity.RoomType;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // Listar las salas de una sede
    List<Room> findByLocation_Id(Long locationId);

    // Buscar salas por tipo
    List<Room> findByType(RoomType type);

    // Buscar salas activas de una sede
    List<Room> findByLocation_IdAndActiveTrue(Long locationId);

    // Verificar si existe el nombre de una sala dentro de una sede
    boolean existsByNameIgnoreCaseAndLocation_Id(
            String name,
            Long locationId
    );

    // Consultar salas con capacidad mínima
    List<Room> findByCapacityGreaterThanEqualAndActiveTrue(Integer capacity);

    // Consultar salas que tengan todos los equipamientos solicitados
    @Query("""
           SELECT r
           FROM Room r
           JOIN r.equipments e
           WHERE e.id IN :equipmentIds
           GROUP BY r
           HAVING COUNT(DISTINCT e.id) = :equipmentCount
           """)
    List<Room> findByAllRequiredEquipment(
            @Param("equipmentIds") Collection<Long> equipmentIds,
            @Param("equipmentCount") Long equipmentCount
    );

    // Consultar salas disponibles en un intervalo
    @Query("""
           SELECT r
           FROM Room r
           WHERE r.location.id = :locationId
           AND r.active = true
           AND (:minCapacity IS NULL OR r.capacity >= :minCapacity)
           AND NOT EXISTS (
               SELECT b.id
               FROM Booking b
               WHERE b.room = r
               AND b.status IN :statuses
               AND b.startTime < :endTime
               AND b.endTime > :startTime
           )
           """)
    List<Room> findAvailableRooms(
            @Param("locationId") Long locationId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("statuses") Collection<BookingStatus> statuses,
            @Param("minCapacity") Integer minCapacity
    );

    // Verificar si una sede tiene salas asociadas
    boolean existsByLocation_Id(Long locationId);

    // Verificar si el nombre pertenece a otra sala
// de la misma sede (excluyendo el ID actual)
    boolean existsByNameIgnoreCaseAndLocation_IdAndIdNot(
            String name,
            Long locationId,
            Long id
    );
    // Comprobar si una sala tiene reservas
    boolean existsByBookings_Id(Long roomId);


}
