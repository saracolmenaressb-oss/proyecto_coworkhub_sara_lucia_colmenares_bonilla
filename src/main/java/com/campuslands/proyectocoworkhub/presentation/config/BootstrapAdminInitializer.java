package com.campuslands.proyectocoworkhub.presentation.config;

import com.campuslands.proyectocoworkhub.persistence.entity.Role;
import com.campuslands.proyectocoworkhub.persistence.entity.UserAccount;
import com.campuslands.proyectocoworkhub.persistence.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BootstrapAdminInitializer implements ApplicationRunner {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.bootstrap-admin.enabled:true}")
    private boolean enabled;

    @Value("${security.bootstrap-admin.username}")
    private String username;

    @Value("${security.bootstrap-admin.password}")
    private String password;

    @Override
    public void run(ApplicationArguments args) {

        if (!enabled) {
            System.out.println("Creación automática del administrador desactivada.");
            return;
        }

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "Debes configurar el username y password del administrador."
            );
        }

        if (userAccountRepository.existsByUsername(username)) {
            System.out.println("El administrador ya existe. No se creará otro.");
            return;
        }

        UserAccount admin = new UserAccount(
                username,
                passwordEncoder.encode(password),
                Role.ADMIN
        );

        userAccountRepository.save(admin);

        System.out.println("Administrador inicial creado correctamente.");
    }
}
