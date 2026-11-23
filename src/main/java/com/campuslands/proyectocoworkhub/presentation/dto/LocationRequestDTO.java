package com.campuslands.proyectocoworkhub.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequestDTO {
    @NotBlank(message = "El nombre de la sede es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String name;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 50, message = "La ciudad no puede superar los 50 caracteres")
    private String city;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 50, message = "La dirección no puede superar los 50 caracteres")
    private String address;

    @NotNull(message = "La hora de apertura es obligatoria")
    private LocalTime openingTime;

    @NotNull(message = "La hora de cierre es obligatoria")
    private LocalTime closingTime;
}
