package com.campuslands.proyectocoworkhub.busisness.service;

import com.campuslands.proyectocoworkhub.presentation.dto.AuthRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(AuthRequestDTO dto);

}
