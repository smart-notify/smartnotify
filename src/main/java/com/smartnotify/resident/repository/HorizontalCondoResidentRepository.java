package com.smartnotify.resident.repository;

import com.smartnotify.resident.model.HorizontalCondoResident;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface HouseResidentRepository extends ResidentBaseRepository<HorizontalCondoResident>,
        JpaRepository<HorizontalCondoResident, String> {

    Optional<HorizontalCondoResident> findByHouseNumberAndCondominiumId(String houseNumber, String condominiumId);
}
