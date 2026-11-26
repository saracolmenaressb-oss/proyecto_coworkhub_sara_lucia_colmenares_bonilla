package com.campuslands.proyectocoworkhub.presentation.mapper;

import com.campuslands.proyectocoworkhub.persistence.entity.Member;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberRegistrationRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberMapper {
    private final MembershipPlanMapper membershipPlanMapper;

    // Convertir DTO a Entity
    public Member toEntity(MemberRequestDTO dto) {

        return new Member(
                dto.documentNumber(),
                dto.fullName(),
                dto.email(),
                dto.status()
        );
    }

    // Convertir Entity a ResponseDTO
    public MemberResponseDTO toResponseDTO(Member member) {

        return new MemberResponseDTO(
                member.getId(),
                member.getDocumentNumber(),
                member.getFullName(),
                member.getEmail(),
                member.getStatus(),
                member.getMembershipPlan().getId(),
                member.getMembershipPlan().getName()
        );
    }
    // Convertir DTO de registro a Entity
    public Member toEntity(MemberRegistrationRequestDTO dto) {

        return new Member(
                dto.documentNumber(),
                dto.fullName(),
                dto.email(),
                dto.status()
        );
    }
}
