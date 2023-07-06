package com.smartnotify.parcel.repository;

import com.smartnotify.parcel.model.Parcel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ParcelRepository extends JpaRepository<Parcel, String> {
}
