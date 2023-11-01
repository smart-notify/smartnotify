package com.smartnotify.resident.usecases.register;

import com.smartnotify.config.exception.ResidentAlreadyExistsException;
import com.smartnotify.resident.model.HorizontalCondoResident;
import com.smartnotify.resident.repository.HorizontalCondoResidentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegisterHorizontalCondoResident {

    private final HorizontalCondoResidentRepository repository;

    @Transactional
    public void execute(final HorizontalCondoResident resident, final String condominiumId) {
        repository.findByEmailAndCondominiumId(resident.getEmail(), condominiumId)
                .ifPresent(retrievedResident -> {
                    throw new ResidentAlreadyExistsException("Email already taken.");
                });

        repository.save(resident);
    }

}
