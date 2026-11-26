package com.campuslands.proyectocoworkhub.presentation.controller;

import com.campuslands.proyectocoworkhub.busisness.service.AuthService;
import com.campuslands.proyectocoworkhub.presentation.dto.AuthProfileDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.AuthRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.AuthResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody AuthRequestDTO dto
    ) {

        return ResponseEntity.ok(authService.login(dto));
    }

    @GetMapping("/perfil")
    public ResponseEntity<AuthProfileDTO> getProfile(
            Authentication authentication
    ) {

        String username = authentication.getName();

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                .orElse("UNKNOWN");

        return ResponseEntity.ok(
                new AuthProfileDTO(username, role)
        );
    }
}
