package com.smartnotify.condominium.service;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.condominium.repository.CondominiumRepository;
import com.smartnotify.config.exception.CondominiumAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CondominiumServiceImplTest {

    @Mock
    private CondominiumRepository condominiumRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CondominiumServiceImpl condominiumService;

    @Test
    void testLoadUserByUsername() {
        final var email = "test@email.com";
        final var condominium = Condominium.builder()
                .email(email)
                .build();

        when(condominiumRepository.findByEmail(email)).thenReturn(Optional.of(condominium));

        UserDetails userDetails = condominiumService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        verify(condominiumRepository, times(1)).findByEmail(email);
    }

    @Test
    void testLoadNonExistingCondominium() {
        final var email = "test@email.com";

        when(condominiumRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> condominiumService.loadUserByUsername(email));

        verify(condominiumRepository, times(1)).findByEmail(email);
    }

    @Test
    void testSignUpCondominium() {
        final var email = "test@email.com";
        final var password = "123";

        final var condominium = Condominium.builder()
                .email(email)
                .password(password)
                .build();

        when(condominiumRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        condominiumService.signUpCondominium(condominium);

        assertEquals("encodedPassword", condominium.getPassword());
        verify(condominiumRepository, times(1)).findByEmail(email);
        verify(condominiumRepository, times(1)).save(condominium);
    }

    @Test
    void testSignUpExistingCondominium() {
        final var email = "test@email.com";

        final var existingCondominium = Condominium.builder()
                .email(email)
                .build();

        final var newCondominium = Condominium.builder()
                .email(email)
                .build();

        when(condominiumRepository.findByEmail(email)).thenReturn(Optional.of(existingCondominium));

        assertThrows(CondominiumAlreadyExistsException.class,
                () -> condominiumService.signUpCondominium(newCondominium));

        verify(condominiumRepository, times(1)).findByEmail(email);
        verify(condominiumRepository, times(0)).save(any(Condominium.class));
    }

}