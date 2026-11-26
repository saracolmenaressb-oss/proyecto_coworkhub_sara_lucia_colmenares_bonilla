package com.campuslands.proyectocoworkhub.presentation.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI coworkHubOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CoWorkHub API")
                        .description("""
                                API REST para la gestión de espacios de coworking.

                                Permite gestionar usuarios, miembros, reservas,
                                salas, sedes, servicios adicionales y autenticación.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo de desarrollo")
                        )
                )
                .components(new Components()
                        .addSecuritySchemes(
                                "bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description(
                                                "Introduce el token JWT obtenido en /auth/login"
                                        )
                        )
                );
    }

    @Bean
    public GroupedOpenApi authGroup() {
        return GroupedOpenApi.builder()
                .group("autenticacion")
                .pathsToMatch("/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi bookingsGroup() {
        return GroupedOpenApi.builder()
                .group("reservas")
                .pathsToMatch("/api/bookings/**")
                .build();
    }

    @Bean
    public GroupedOpenApi membersGroup() {
        return GroupedOpenApi.builder()
                .group("miembros")
                .pathsToMatch("/api/members/**")
                .build();
    }

    @Bean
    public GroupedOpenApi generalGroup() {
        return GroupedOpenApi.builder()
                .group("general")
                .pathsToMatch("/api/**")
                .pathsToExclude(
                        "/api/bookings/**",
                        "/api/members/**"
                )
                .build();
    }

}
