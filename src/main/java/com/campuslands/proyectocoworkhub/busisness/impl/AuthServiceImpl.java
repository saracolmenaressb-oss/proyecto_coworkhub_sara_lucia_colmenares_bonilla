package com.campuslands.proyectocoworkhub.busisness.impl;

import com.campuslands.proyectocoworkhub.busisness.service.AuthService;
import com.campuslands.proyectocoworkhub.persistence.entity.UserAccount;
import com.campuslands.proyectocoworkhub.persistence.repository.UserAccountRepository;
import com.campuslands.proyectocoworkhub.presentation.dto.AuthRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.AuthResponseDTO;
import com.campuslands.proyectocoworkhub.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserAccountRepository userAccountRepository;
    private final JwtService jwtService;
    @Value("${security.jwt.expiration-ms:3600000}")
    private long expirationMs;

    @Override
    public AuthResponseDTO login(AuthRequestDTO dto) {

        // 1. Verificar las credenciales.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.username(),
                        dto.password()
                )
        );

        // 2. Buscar la cuenta autenticada.
        UserAccount account = userAccountRepository
                .findByUsername(dto.username())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No existe una cuenta con ese usuario"
                ));

        // 3. Generar el token JWT.
        String token = jwtService.generateToken(
                account.getUsername(),
                account.getRole().name()
        );

        // 4. Construir la respuesta.
        return new AuthResponseDTO(
                token,
                "Bearer",
                expirationMs,
                account.getUsername(),
                account.getRole().name()
        );
    }
}
