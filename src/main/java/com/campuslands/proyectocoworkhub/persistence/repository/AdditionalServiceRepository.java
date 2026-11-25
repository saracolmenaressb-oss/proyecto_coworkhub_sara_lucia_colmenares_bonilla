package com.campuslands.proyectocoworkhub.persistence.repository;

import com.campuslands.proyectocoworkhub.persistence.entity.AdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Long> {
    // Buscar un servicio por nombre
    Optional<AdditionalService> findByNameIgnoreCase(String name);

    // Verificar si existe un servicio por nombre
    boolean existsByNameIgnoreCase(String name);

    // Comprobar si el servicio está siendo utilizado en alguna reserva
    @Query("""
           SELECT CASE WHEN COUNT(bi) > 0 THEN true ELSE false END
           FROM BookingItem bi
           WHERE bi.additionalService.id = :serviceId
           """)
    boolean isServiceInUse(@Param("serviceId") Long serviceId);
    //
    @Query("""
        SELECT DISTINCT bi.additionalService
        FROM BookingItem bi
        WHERE bi.booking.id = :bookingId
        """)
    List<AdditionalService> findAdditionalServicesByBookingId(
            @Param("bookingId") Long bookingId
    );
}
