package com.smartnotify.resident.repository;

import com.smartnotify.resident.model.HorizontalCondoResident;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface HorizontalCondoResidentRepository extends ResidentBaseRepository<HorizontalCondoResident>,
        JpaRepository<HorizontalCondoResident, String> {

    List<HorizontalCondoResident> findAllByHouseNumberAndCondominiumId(String houseNumber,
                                                                       String condominiumId);

    Long countByHouseNumberAndCondominiumId(String houseNumber, String condominiumId);
}
