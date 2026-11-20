package com.campuslands.proyectocoworkhub.presentation.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StartupConfigValidator {
    @Value("${spring.application.name:}")
    private String applicationName;

    @Value("${coworkhub.booking.max-advance-days:0}")
    private int maxAdvanceDays;

    @Value("${coworkhub.time-zone:}")
    private String timeZone;

    @PostConstruct
    public void validateConfiguration() {

        if (applicationName.isBlank()) {
            throw new IllegalStateException(
                    "El nombre de la aplicación es obligatorio"
            );
        }

        if (maxAdvanceDays <= 0) {
            throw new IllegalStateException(
                    "Los días máximos de reserva deben ser mayores a cero"
            );
        }

        if (timeZone.isBlank()) {
            throw new IllegalStateException(
                    "La zona horaria es obligatoria"
            );
        }

        System.out.println("Configuraciones validadas correctamente");
    }
}
