package com.campuslands.proyectocoworkhub.persistence.repository;

import com.campuslands.proyectocoworkhub.persistence.entity.Member;
import com.campuslands.proyectocoworkhub.persistence.entity.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // Buscar por documento
    Optional<Member> findByDocumentNumber(String documentNumber);

    // Buscar por correo
    Optional<Member> findByEmail(String email);

    // Filtrar por nombre
    List<Member> findByFullNameContainingIgnoreCase(String fullName);

    // Filtrar por estado
    List<Member> findByStatus(MemberStatus status);

    // Filtrar por nombre y estado
    List<Member> findByFullNameContainingIgnoreCaseAndStatus(
            String fullName,
            MemberStatus status
    );
}
