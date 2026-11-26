package com.campuslands.proyectocoworkhub.presentation.controller;

import com.campuslands.proyectocoworkhub.busisness.service.MemberService;
import com.campuslands.proyectocoworkhub.persistence.entity.MemberStatus;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberRegistrationRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Validated
@PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
public class MemberController {
    private final MemberService memberService;

    // 1. Registrar un miembro y crear sus credenciales.
    @PostMapping
    public ResponseEntity<MemberResponseDTO> registrar(
            @Valid @RequestBody MemberRegistrationRequestDTO dto) {

        MemberResponseDTO response = memberService.registrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. Buscar miembro por ID.
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> buscarPorId(
            @PathVariable @Positive Long id) {

        return ResponseEntity.ok(memberService.buscarPorId(id));
    }

    // 3. Buscar miembro por documento.
    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<MemberResponseDTO> buscarPorDocumento(
            @PathVariable String documentNumber) {

        return ResponseEntity.ok(
                memberService.buscarPorDocumento(documentNumber)
        );
    }

    // 4. Buscar miembro por correo.
    @GetMapping("/email")
    public ResponseEntity<MemberResponseDTO> buscarPorCorreo(
            @RequestParam String email) {

        return ResponseEntity.ok(
                memberService.buscarPorCorreo(email)
        );
    }

    // 5. Buscar miembros por nombre.
    @GetMapping("/search")
    public ResponseEntity<List<MemberResponseDTO>> buscarPorNombre(
            @RequestParam String fullName) {

        return ResponseEntity.ok(
                memberService.buscarPorNombre(fullName)
        );
    }

    // 6. Buscar miembros por estado.
    @GetMapping("/status/{status}")
    public ResponseEntity<List<MemberResponseDTO>> buscarPorEstado(
            @PathVariable MemberStatus status) {

        return ResponseEntity.ok(
                memberService.buscarPorEstado(status)
        );
    }

    // 7. Buscar miembros por nombre y estado.
    @GetMapping("/filter")
    public ResponseEntity<List<MemberResponseDTO>> buscarPorNombreYEstado(
            @RequestParam String fullName,
            @RequestParam MemberStatus status) {

        return ResponseEntity.ok(
                memberService.buscarPorNombreYEstado(fullName, status)
        );
    }

    // 8. Actualizar información del miembro.
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> actualizar(
            @PathVariable @Positive Long id,
            @Valid @RequestBody MemberRequestDTO dto) {

        return ResponseEntity.ok(
                memberService.actualizar(id, dto)
        );
    }

    // 9. Eliminar miembro.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive Long id) {

        memberService.deleteMember(id);

        return ResponseEntity.noContent().build();
    }
}
