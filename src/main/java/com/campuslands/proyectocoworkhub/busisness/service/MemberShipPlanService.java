package com.campuslands.proyectocoworkhub.busisness.service;

import com.campuslands.proyectocoworkhub.presentation.dto.MemberShipPlanRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberShipPlanResponseDTO;

import java.util.List;
import java.util.Optional;

public interface MemberShipPlanService {
    // Registrar un nuevo plan de membresía
    MemberShipPlanResponseDTO createMembershipPlan(
            MemberShipPlanRequestDTO dto
    );

    // Buscar un plan por su identificador
    MemberShipPlanResponseDTO getMembershipPlanById(Long id);

    // Listar todos los planes registrados
    List<MemberShipPlanResponseDTO> getAllMembershipPlans();

    // Actualizar los datos de un plan existente
    MemberShipPlanResponseDTO updateMembershipPlan(
            Long id,
            MemberShipPlanRequestDTO dto
    );

    // Eliminar un plan, siempre que no tenga miembros asociados
    void deleteMembershipPlan(Long id);


}
