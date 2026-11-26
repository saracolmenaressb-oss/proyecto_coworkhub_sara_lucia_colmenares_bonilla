package com.campuslands.proyectocoworkhub.presentation.dto;

import com.campuslands.proyectocoworkhub.persistence.entity.MemberStatus;

public record MemberRequestDTO (
        String documentNumber,
        String fullName,
        String email,
        MemberStatus status,
        Long membershipPlanId,
        String username,
        String password
){
}
