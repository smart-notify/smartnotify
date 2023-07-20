package com.smartnotify.parcel.repository;

import com.smartnotify.parcel.model.DeliveryStatus;
import com.smartnotify.parcel.model.Parcel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ParcelRepository extends JpaRepository<Parcel, String> {

    List<Parcel> findAllByStatusAndCondominiumId(DeliveryStatus status, String condominiumId);

    List<Parcel> findAllByResidenceDetailsAndCondominiumId(String residenceDetails, String condominiumId);

    @Modifying
    @Query(
            value = """
                    UPDATE parcel
                        SET status = 'DELIVERED',
                        delivery_date = CONVERT_TZ(CURRENT_TIMESTAMP(6), 'UTC', 'America/Sao_Paulo')
                    WHERE
                        id = :id
                        AND delivery_code = :deliveryCode
                        AND status = 'NOT_DELIVERED'
                        AND condominium_id = :condominiumId
                    """,
            nativeQuery = true)
    Integer deliverParcel(String id, String deliveryCode, String condominiumId);

}
