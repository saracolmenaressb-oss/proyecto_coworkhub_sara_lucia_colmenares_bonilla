package com.campuslands.proyectocoworkhub.presentation.controller;

import com.campuslands.proyectocoworkhub.busisness.service.MemberShipPlanService;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberShipPlanRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberShipPlanResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membership-plans")
@RequiredArgsConstructor
public class MemberShipPlanController {
    private final MemberShipPlanService membershipPlanService;

    // Registrar un nuevo plan
    @PostMapping
    public ResponseEntity<MemberShipPlanResponseDTO> createMembershipPlan(
            @Valid @RequestBody MemberShipPlanRequestDTO dto) {

        MemberShipPlanResponseDTO response =
                membershipPlanService.createMembershipPlan(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Buscar un plan por ID
    @GetMapping("/{id}")
    public ResponseEntity<MemberShipPlanResponseDTO> getMembershipPlanById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                membershipPlanService.getMembershipPlanById(id)
        );
    }

    // Listar todos los planes
    @GetMapping
    public ResponseEntity<List<MemberShipPlanResponseDTO>> getAllMembershipPlans() {

        return ResponseEntity.ok(
                membershipPlanService.getAllMembershipPlans()
        );
    }

    // Actualizar un plan
    @PutMapping("/{id}")
    public ResponseEntity<MemberShipPlanResponseDTO> updateMembershipPlan(
            @PathVariable Long id,
            @Valid @RequestBody MemberShipPlanRequestDTO dto) {

        MemberShipPlanResponseDTO response =
                membershipPlanService.updateMembershipPlan(id, dto);

        return ResponseEntity.ok(response);
    }

    // Eliminar un plan
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembershipPlan(
            @PathVariable Long id) {

        membershipPlanService.deleteMembershipPlan(id);

        return ResponseEntity.noContent().build();
    }
}
