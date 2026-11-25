package com.campuslands.proyectocoworkhub.presentation.config;


import com.campuslands.proyectocoworkhub.security.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.OffsetDateTime;
import java.util.Map;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                // La API utilizará JWT, no sesiones ni formularios.
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Definir las rutas públicas y protegidas.
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/auth/login",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        .requestMatchers(
                                org.springframework.http.HttpMethod.OPTIONS,
                                "/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )

                // Respuestas JSON cuando falte autenticación o permisos.
                .exceptionHandling(exceptions -> exceptions

                        .authenticationEntryPoint((request, response, exception) -> {

                            response.setStatus(401);
                            response.setContentType(
                                    MediaType.APPLICATION_JSON_VALUE
                            );

                            response.setHeader(
                                    HttpHeaders.WWW_AUTHENTICATE,
                                    "Bearer"
                            );

                            objectMapper.writeValue(
                                    response.getOutputStream(),
                                    Map.of(
                                            "error", "Se requiere autenticación",
                                            "codigo", 401,
                                            "timestamp", OffsetDateTime.now().toString()
                                    )
                            );
                        })

                        .accessDeniedHandler((request, response, exception) -> {

                            response.setStatus(403);
                            response.setContentType(
                                    MediaType.APPLICATION_JSON_VALUE
                            );

                            objectMapper.writeValue(
                                    response.getOutputStream(),
                                    Map.of(
                                            "error", "No tienes permisos para realizar esta operación",
                                            "codigo", 403,
                                            "timestamp", OffsetDateTime.now().toString()
                                    )
                            );
                        })
                )

                // Ejecutar nuestro filtro JWT antes del filtro de usuario y contraseña.
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }
}
