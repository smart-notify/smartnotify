package com.smartnotify.resident.repository;

import com.smartnotify.resident.model.VerticalCondoResident;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface VerticalCondoResidentRepository extends ResidentBaseRepository<VerticalCondoResident>,
        JpaRepository<VerticalCondoResident, String> {

    List<VerticalCondoResident> findAllByApartmentNumberAndBlockAndCondominiumId(String apartmentNumber,
                                                                                 String block,
                                                                                 String condominiumId);

    Long countByApartmentNumberAndBlockAndCondominiumId(String apartmentNumber, String block, String condominiumId);

}
