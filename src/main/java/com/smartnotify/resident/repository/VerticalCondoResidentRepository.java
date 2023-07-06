package com.smartnotify.resident.repository;

import com.smartnotify.resident.model.VerticalCondoResident;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface VerticalCondoResidentRepository extends ResidentBaseRepository<VerticalCondoResident>,
        JpaRepository<VerticalCondoResident, String> {

    Optional<VerticalCondoResident> findByApartmentNumberAndBlockAndCondominiumId(String apartmentNumber,
                                                                                  String block,
                                                                                  String condominiumId);
}
