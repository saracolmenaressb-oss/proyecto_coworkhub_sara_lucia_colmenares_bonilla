package com.campuslands.proyectocoworkhub.presentation.dto;

import com.campuslands.proyectocoworkhub.persistence.entity.MemberStatus;

public record MemberResponseDTO (
        Long id,
        String documentNumber,
        String fullName,
        String email,
        MemberStatus status,
        Long membershipPlanId,
        String membershipPlanName
){
}
