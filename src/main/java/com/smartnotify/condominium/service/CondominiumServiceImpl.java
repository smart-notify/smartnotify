package com.smartnotify.condominium.service;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.condominium.repository.CondominiumRepository;
import com.smartnotify.config.exception.CondominiumAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CondominiumServiceImpl implements UserDetailsService, CondominiumService {

    private final CondominiumRepository condominiumRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return condominiumRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Condominium with email %s not found.", email)));
    }

    @Override
    public void signUpCondominium(final Condominium condominium) {
        condominiumRepository.findByEmail(condominium.getEmail()).ifPresent(retrievedCondominium -> {
            throw new CondominiumAlreadyExistsException("Email already taken.");
        });
        condominium.setPassword(passwordEncoder.encode(condominium.getPassword()));
        condominiumRepository.save(condominium);
    }

}
