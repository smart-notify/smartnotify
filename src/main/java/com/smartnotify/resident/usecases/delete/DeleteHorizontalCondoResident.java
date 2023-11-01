package com.smartnotify.resident.usecases.delete;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.config.exception.ResidentNotFoundException;
import com.smartnotify.parcel.model.DeliveryStatus;
import com.smartnotify.parcel.model.Parcel;
import com.smartnotify.parcel.repository.ParcelRepository;
import com.smartnotify.resident.model.HorizontalCondoResident;
import com.smartnotify.resident.repository.HorizontalCondoResidentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeleteHorizontalCondoResident implements DeleteResident {

    private final ParcelRepository parcelRepository;
    private final HorizontalCondoResidentRepository residentRepository;

    @Transactional
    public void execute(final String email, final String condominiumId) {
        final HorizontalCondoResident resident = findResident(email, condominiumId);

        final Long amountOfResidents =
                getAmountOfResidents(resident.getHouseNumber(), condominiumId);

        final boolean hasNotDeliveredParcels = hasNotDeliveredParcels(resident.getHouseNumber(), condominiumId);

        if (amountOfResidents == 1 && hasNotDeliveredParcels) {
            throw new IllegalStateException("Error deleting resident " + resident.getName() +
                    " because there is only one resident registered in the residence and there are pending parcels." +
                    " Make sure to deliver all the parcels before deleting the resident.");
        }
        residentRepository.deleteByEmailAndCondominiumId(email, condominiumId);
    }

    private boolean hasNotDeliveredParcels(final String houseNumber, final String condominiumId) {
        final List<Parcel> parcels = parcelRepository
                .findAllByResidenceDetailsAndCondominiumId(houseNumber, condominiumId);

        return parcels.stream()
                .anyMatch(parcel -> parcel.getStatus() == DeliveryStatus.NOT_DELIVERED);
    }

    private HorizontalCondoResident findResident(final String email, final String condominiumId) {
        return (HorizontalCondoResident) residentRepository.findByEmailAndCondominiumId(email, condominiumId)
                .orElseThrow(() -> new ResidentNotFoundException("Resident not found"));
    }

    private Long getAmountOfResidents(final String houseNumber, final String condominiumId) {
        return residentRepository
                .countByHouseNumberAndCondominiumId(houseNumber, condominiumId);
    }

    @Override
    public CondominiumType getCondominiumType() {
        return CondominiumType.HORIZONTAL;
    }

}
