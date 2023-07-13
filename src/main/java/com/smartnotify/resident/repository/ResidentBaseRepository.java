package com.smartnotify.resident.repository;

import com.smartnotify.resident.model.Resident;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface ResidentBaseRepository<T extends Resident> extends Repository<T, String> {
    Optional<Resident> findByEmail(String email);

    void deleteByEmailAndCondominiumId(String email, String condominiumId);

}
