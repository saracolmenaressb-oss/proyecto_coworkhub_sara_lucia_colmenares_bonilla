package com.campuslands.proyectocoworkhub.busisness.impl;

import com.campuslands.proyectocoworkhub.busisness.service.MemberShipPlanService;
import com.campuslands.proyectocoworkhub.persistence.entity.MembershipPlan;
import com.campuslands.proyectocoworkhub.persistence.repository.MembershipPlanRepository;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberShipPlanRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberShipPlanResponseDTO;
import com.campuslands.proyectocoworkhub.presentation.exception.ResourceInUseException;
import com.campuslands.proyectocoworkhub.presentation.mapper.MembershipPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberShipPlanServiceImpl implements MemberShipPlanService {
    private final MembershipPlanMapper membershipPlanMapper;
    private final MembershipPlanRepository membershipPlanRepository;

    // Registrar un nuevo plan de membresía
    @Override
    public MemberShipPlanResponseDTO createMembershipPlan(
            MemberShipPlanRequestDTO dto) {

        String name = dto.name().trim();

        // Verificar si ya existe un plan con ese nombre
        if (membershipPlanRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un plan con ese nombre"
            );
        }

        // Crear un DTO normalizado
        MemberShipPlanRequestDTO normalizedDTO =
                new MemberShipPlanRequestDTO(
                        name,
                        dto.includedHoursPerMonth(),
                        dto.overageDiscount(),
                        dto.maxActiveBookings()
                );

        // Convertir el DTO en entidad
        MembershipPlan membershipPlan =
                membershipPlanMapper.toEntity(normalizedDTO);

        // Guardar la entidad
        MembershipPlan savedMembershipPlan =
                membershipPlanRepository.save(membershipPlan);

        // Convertir la entidad guardada en DTO de respuesta
        return membershipPlanMapper.toResponseDTO(savedMembershipPlan);
    }

    // Buscar un plan por su identificador
    @Override
    public MemberShipPlanResponseDTO getMembershipPlanById(Long id) {

        MembershipPlan membershipPlan =
                membershipPlanRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No se encontró el plan de membresía"
                        ));

        return membershipPlanMapper.toResponseDTO(membershipPlan);
    }

    // Listar todos los planes registrados
    @Override
    public List<MemberShipPlanResponseDTO> getAllMembershipPlans() {

        return membershipPlanRepository.findAll()
                .stream()
                .map(membershipPlanMapper::toResponseDTO)
                .toList();
    }

    // Actualizar los datos de un plan existente
    @Override
    public MemberShipPlanResponseDTO updateMembershipPlan(
            Long id,
            MemberShipPlanRequestDTO dto) {

        // Buscar el plan que se desea actualizar
        MembershipPlan membershipPlan =
                membershipPlanRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No se encontró el plan de membresía"
                        ));

        String name = dto.name().trim();

        // Comprobar si el nombre pertenece a otro plan
        membershipPlanRepository.findByNameIgnoreCase(name)
                .filter(existingPlan -> !existingPlan.getId().equals(id))
                .ifPresent(existingPlan -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Ya existe otro plan con ese nombre"
                    );
                });

        // Actualizar los atributos de la entidad
        membershipPlan.setName(name);
        membershipPlan.setIncludedHourPerMonth(dto.includedHoursPerMonth());
        membershipPlan.setOverageDiscount(dto.overageDiscount());
        membershipPlan.setMaxActiveBooking(dto.maxActiveBookings());

        // Guardar los cambios
        MembershipPlan updatedMembershipPlan =
                membershipPlanRepository.save(membershipPlan);

        return membershipPlanMapper.toResponseDTO(updatedMembershipPlan);
    }

    // Eliminar un plan si no tiene miembros asociados
    @Override
    public void deleteMembershipPlan(Long id) {

        // Comprobar que el plan exista
        MembershipPlan membershipPlan =
                membershipPlanRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No se encontró el plan de membresía"
                        ));

        // Comprobar si el plan está siendo utilizado
        if (membershipPlanRepository.isPlanInUse(id)) {
            throw new ResourceInUseException(
                    "No se puede eliminar el plan porque tiene miembros asociados"
            );
        }

        // Eliminar el plan
        membershipPlanRepository.delete(membershipPlan);
    }
}
