package com.campuslands.proyectocoworkhub.persistence.repository;

import com.campuslands.proyectocoworkhub.persistence.entity.Booking;
import com.campuslands.proyectocoworkhub.persistence.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Buscar reservas de un miembro
    List<Booking> findByMember_Id(Long memberId);

    // Buscar reservas de una sala
    List<Booking> findByRoom_Id(Long roomId);

    // Buscar reservas de una sala en un día determinado
    List<Booking> findByRoom_IdAndStartTimeGreaterThanEqualAndStartTimeLessThan(
            Long roomId,
            LocalDateTime startOfDay,
            LocalDateTime startOfNextDay
    );

    // Filtrar reservas por estado
    List<Booking> findByStatus(BookingStatus status);

    // Buscar reservas activas futuras de un miembro
    List<Booking> findByMember_IdAndStatusInAndStartTimeAfter(
            Long memberId,
            Collection<BookingStatus> statuses,
            LocalDateTime now
    );

    // Contar reservas activas futuras de un miembro
    long countByMember_IdAndStatusInAndStartTimeAfter(
            Long memberId,
            Collection<BookingStatus> statuses,
            LocalDateTime now
    );

    // Consultar reservas que se superponen con un intervalo
    @Query("""
           SELECT b
           FROM Booking b
           WHERE b.room.id = :roomId
           AND b.status IN :statuses
           AND b.startTime < :requestedEnd
           AND b.endTime > :requestedStart
           """)
    List<Booking> findOverlappingBookings(
            @Param("roomId") Long roomId,
            @Param("statuses") Collection<BookingStatus> statuses,
            @Param("requestedStart") LocalDateTime requestedStart,
            @Param("requestedEnd") LocalDateTime requestedEnd
    );

    // Obtener reservas que se cruzan con un período mensual
    @Query("""
           SELECT b
           FROM Booking b
           WHERE b.member.id = :memberId
           AND b.status IN :statuses
           AND b.startTime < :monthEnd
           AND b.endTime > :monthStart
           """)
    List<Booking> findBookingsForMonthlyConsumption(
            @Param("memberId") Long memberId,
            @Param("statuses") Collection<BookingStatus> statuses,
            @Param("monthStart") LocalDateTime monthStart,
            @Param("monthEnd") LocalDateTime monthEnd
    );
    //No eliminar salas con reservas:
    boolean existsByRoom_Id(Long roomId);

    // Comprobar si un miembro tiene reservas
    boolean existsByMember_Id(Long memberId);

    @Query("""
       SELECT b
       FROM Booking b
       WHERE (:memberId IS NULL OR b.member.id = :memberId)
       AND (:roomId IS NULL OR b.room.id = :roomId)
       AND (
           :startOfDay IS NULL
           OR (
               b.startTime < :nextDay
               AND b.endTime > :startOfDay
           )
       )
       AND (:status IS NULL OR b.status = :status)
       ORDER BY b.startTime ASC
       """)
    List<Booking> findFilteredBookings(
            @Param("memberId") Long memberId,
            @Param("roomId") Long roomId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("nextDay") LocalDateTime nextDay,
            @Param("status") BookingStatus status
    );

}
