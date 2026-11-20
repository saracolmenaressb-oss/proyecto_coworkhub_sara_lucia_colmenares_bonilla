package com.campuslands.proyectocoworkhub.persistence.repository;

import com.campuslands.proyectocoworkhub.persistence.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    // Buscar por nombre de usuario
    Optional<UserAccount> findByUsername(String username);

    // Verificar si existe un nombre de usuario
    boolean existsByUsername(String username);

    // Buscar la cuenta asociada a un miembro
    Optional<UserAccount> findByMember_Id(Long memberId);
}
