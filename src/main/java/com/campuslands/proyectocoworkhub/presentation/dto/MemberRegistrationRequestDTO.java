package com.campuslands.proyectocoworkhub.presentation.dto;

import com.campuslands.proyectocoworkhub.persistence.entity.MemberStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MemberRegistrationRequestDTO (
        @NotBlank(message = "El documento es obligatorio")
        @Size(max = 20)
        String documentNumber,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100)
        String fullName,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato válido")
        @Size(max = 150)
        String email,

        @NotNull(message = "El estado es obligatorio")
        MemberStatus status,

        @NotNull(message = "El plan de membresía es obligatorio")
        Long membershipPlanId,

        @NotBlank(message = "El username es obligatorio")
        @Size(max = 50)
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, max = 72,
                message = "La contraseña debe tener entre 8 y 72 caracteres")
        String password
){

}
