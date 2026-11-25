package com.campuslands.proyectocoworkhub.busisness.impl;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.campuslands.proyectocoworkhub.persistence.entity.MemberStatus;
import com.campuslands.proyectocoworkhub.persistence.entity.UserAccount;
import com.campuslands.proyectocoworkhub.persistence.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserAccount account = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No existe una cuenta con ese nombre de usuario"
                ));

        boolean enabled = account.getMember() == null ||
                account.getMember().getStatus() == MemberStatus.ACTIVE;

        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .authorities("ROLE_" + account.getRole().name())
                .disabled(!enabled)
                .build();
    }
}
