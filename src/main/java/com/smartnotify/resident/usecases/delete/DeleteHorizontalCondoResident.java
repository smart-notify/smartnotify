package com.smartnotify.resident.usecases.delete;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.config.exception.ResidentNotFoundException;
import com.smartnotify.parcel.model.DeliveryStatus;
import com.smartnotify.resident.repository.HorizontalCondoResidentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteHorizontalCondoResident implements DeleteResident {

    private final HorizontalCondoResidentRepository repository;

    @Transactional
    public void execute(final String email, String condominiumId) {
        final var resident = repository.findByEmail(email)
                .orElseThrow(() -> new ResidentNotFoundException("Resident not found"));

        final var hasNotDeliveredParcels = resident.getParcels().stream()
                .anyMatch(parcel -> parcel.getStatus() == DeliveryStatus.NOT_DELIVERED);

        if (hasNotDeliveredParcels) {
            throw new IllegalStateException("Error deleting resident " + resident.getName() +
                    " because of pending parcels. Make sure to deliver all the parcels before deleting the resident.");
        }
        repository.deleteByEmailAndCondominiumId(resident.getEmail(), condominiumId);
    }

    @Override
    public CondominiumType getCondominiumType() {
        return CondominiumType.HORIZONTAL;
    }

}
