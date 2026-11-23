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
public class LocationResponseDTO {
    private Long id;
    private String name;
    private String city;
    private String address;
    private LocalTime openingTime;
    private LocalTime closingTime;
}
