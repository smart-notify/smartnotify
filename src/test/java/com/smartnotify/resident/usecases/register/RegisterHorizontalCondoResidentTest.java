package com.smartnotify.resident.usecases.register;

import com.smartnotify.config.exception.ResidentAlreadyExistsException;
import com.smartnotify.resident.model.HorizontalCondoResident;
import com.smartnotify.resident.repository.HorizontalCondoResidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterHorizontalCondoResidentTest {

    @Mock
    private HorizontalCondoResidentRepository repository;

    @InjectMocks
    private RegisterHorizontalCondoResident registerResident;

    private HorizontalCondoResident resident;

    @BeforeEach
    void setup() {
        resident = new HorizontalCondoResident();
        resident.setEmail("email@email.com");
    }

    @Test
    void testRegisterResident() {
        final var condominiumId = "condominiumId";

        when(repository.findByEmailAndCondominiumId(resident.getEmail(), condominiumId))
                .thenReturn(Optional.empty());

        registerResident.execute(resident, condominiumId);

        verify(repository).findByEmailAndCondominiumId(resident.getEmail(), condominiumId);
        verify(repository).save(resident);
    }

    @Test
    void testRegisterResidentThatAlreadyExists() {
        final var condominiumId = "condominiumId";

        when(repository.findByEmailAndCondominiumId(resident.getEmail(), condominiumId))
                .thenReturn(Optional.of(resident));

        final var exception = assertThrows(ResidentAlreadyExistsException.class,
                () -> registerResident.execute(resident, condominiumId));
        assertEquals("Email already taken.", exception.getMessage());
    }

}