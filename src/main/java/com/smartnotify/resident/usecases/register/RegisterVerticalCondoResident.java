package com.smartnotify.resident.usecases.register;

import com.smartnotify.config.exception.ResidentAlreadyExistsException;
import com.smartnotify.resident.model.VerticalCondoResident;
import com.smartnotify.resident.repository.VerticalCondoResidentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegisterVerticalCondoResident {

    private final VerticalCondoResidentRepository repository;

    @Transactional
    public void execute(final VerticalCondoResident resident, final String condominiumId) {
        repository.findByEmailAndCondominiumId(resident.getEmail(), condominiumId)
                .ifPresent(retrievedResident -> {
                    throw new ResidentAlreadyExistsException("Email already taken.");
                });

        repository.save(resident);
    }

}
