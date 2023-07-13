package com.smartnotify.resident.service;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.config.exception.ResidentNotFoundException;
import com.smartnotify.residence.factory.ResidenceDataNormalizerFactory;
import com.smartnotify.resident.api.json.RegisterHorizontalCondoResidentRequest;
import com.smartnotify.resident.model.HorizontalCondoResident;
import com.smartnotify.resident.model.Resident;
import com.smartnotify.resident.repository.HorizontalCondoResidentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HorizontalCondoResidentService implements ResidentService {

    private final ResidenceDataNormalizerFactory normalizer;
    private final HorizontalCondoResidentRepository repository;

    public HorizontalCondoResident buildHorizontalCondoResident(final RegisterHorizontalCondoResidentRequest request) {
        final var strategy = normalizer.findStrategy(Condominium.getAuthenticatedCondominium().getType());
        final var houseNumber = strategy.normalize(request.getHouseNumber());

        final var resident = HorizontalCondoResident.builder()
                .houseNumber(houseNumber)
                .build();

        resident.setName(request.getName());
        resident.setEmail(request.getEmail());
        resident.setCondominium(Condominium.getAuthenticatedCondominium());

        return resident;
    }

    @Override
    public Resident findResidentByResidenceDetails(String residenceDetails, final String condominiumId) {
        return repository.findByHouseNumberAndCondominiumId(residenceDetails, condominiumId)
                .orElseThrow(() -> new ResidentNotFoundException("Resident not found."));
    }

    @Override
    public CondominiumType getCondominiumType() {
        return CondominiumType.HORIZONTAL;
    }

}
