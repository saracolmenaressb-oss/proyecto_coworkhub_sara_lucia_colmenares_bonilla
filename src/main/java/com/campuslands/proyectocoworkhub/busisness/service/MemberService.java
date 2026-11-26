package com.campuslands.proyectocoworkhub.busisness.service;

import com.campuslands.proyectocoworkhub.persistence.entity.MemberStatus;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberRegistrationRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberResponseDTO;

import java.util.List;

public interface MemberService {
    // Registrar un miembro
    MemberResponseDTO registrar(MemberRegistrationRequestDTO dto);

    // Buscar miembro por ID
    MemberResponseDTO buscarPorId(Long id);

    // Buscar miembro por documento
    MemberResponseDTO buscarPorDocumento(String documentNumber);

    // Buscar miembro por correo
    MemberResponseDTO buscarPorCorreo(String email);

    // Listar miembros por nombre
    List<MemberResponseDTO> buscarPorNombre(String fullName);

    // Listar miembros por estado
    List<MemberResponseDTO> buscarPorEstado(MemberStatus status);

    // Filtrar por nombre y estado
    List<MemberResponseDTO> buscarPorNombreYEstado(
            String fullName,
            MemberStatus status
    );

    // Actualizar miembro
    MemberResponseDTO actualizar(Long id, MemberRequestDTO dto);
    //Eliminar miembro
    void deleteMember(Long id);
}
