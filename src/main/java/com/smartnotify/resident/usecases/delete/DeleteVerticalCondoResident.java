package com.smartnotify.resident.usecases.delete;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.config.exception.ResidentNotFoundException;
import com.smartnotify.parcel.model.DeliveryStatus;
import com.smartnotify.parcel.model.Parcel;
import com.smartnotify.parcel.repository.ParcelRepository;
import com.smartnotify.resident.model.VerticalCondoResident;
import com.smartnotify.resident.repository.VerticalCondoResidentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeleteVerticalCondoResident implements DeleteResident {

    private final ParcelRepository parcelRepository;
    private final VerticalCondoResidentRepository residentRepository;

    @Transactional
    public void execute(final String email, final String condominiumId) {
        final VerticalCondoResident resident = findResident(email, condominiumId);
        final String residenceDetails = buildResidenceDetails(resident);

        final Long amountOfResidents =
                getAmountOfResidents(resident.getApartmentNumber(), resident.getBlock(), condominiumId);

        final boolean hasNotDeliveredParcels = hasNotDeliveredParcels(residenceDetails, condominiumId);

        if (amountOfResidents == 1 && hasNotDeliveredParcels) {
            throw new IllegalStateException("Error deleting resident " + resident.getName() +
                    " because there is only one resident registered in the residence and there are pending parcels." +
                    " Make sure to deliver all the parcels before deleting the resident.");
        }
        residentRepository.deleteByEmailAndCondominiumId(email, condominiumId);
    }

    @Override
    public CondominiumType getCondominiumType() {
        return CondominiumType.VERTICAL;
    }

    private boolean hasNotDeliveredParcels(final String residenceDetails, final String condominiumId) {
        final List<Parcel> parcels = parcelRepository
                .findAllByResidenceDetailsAndCondominiumId(residenceDetails, condominiumId);

        return parcels.stream()
                .anyMatch(parcel -> parcel.getStatus() == DeliveryStatus.NOT_DELIVERED);
    }

    private Long getAmountOfResidents(final String apartmentNumber, final String block, final String condominiumId) {
        return residentRepository
                .countByApartmentNumberAndBlockAndCondominiumId(apartmentNumber, block, condominiumId);
    }

    private VerticalCondoResident findResident(final String email, final String condominiumId) {
        return (VerticalCondoResident) residentRepository.findByEmailAndCondominiumId(email, condominiumId)
                .orElseThrow(() -> new ResidentNotFoundException("Resident not found"));
    }

    private String buildResidenceDetails(final VerticalCondoResident resident) {
        return resident.getBlock() == null ?
                resident.getApartmentNumber() :
                resident.getApartmentNumber() + " " + resident.getBlock();
    }

}
