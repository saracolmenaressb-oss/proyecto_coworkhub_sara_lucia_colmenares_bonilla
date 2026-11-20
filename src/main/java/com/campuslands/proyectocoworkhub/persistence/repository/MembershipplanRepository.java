package com.campuslands.proyectocoworkhub.persistence.repository;

import com.campuslands.proyectocoworkhub.persistence.entity.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MembershipplanRepository extends JpaRepository<MembershipPlan, Long> {
    // Buscar un plan por nombre
    Optional<MembershipPlan> findByNameIgnoreCase(String name);

    // Verificar si existe un plan por nombre
    boolean existsByNameIgnoreCase(String name);

    // Comprobar si el plan tiene miembros asociados
    @Query("""
           SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END
           FROM Member m
           WHERE m.membershipPlan.id = :planId
           """)
    boolean isPlanInUse(@Param("planId") Long planId);
}
