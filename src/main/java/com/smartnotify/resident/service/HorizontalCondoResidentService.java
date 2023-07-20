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

import java.util.ArrayList;
import java.util.List;

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
    public List<Resident> findResidentsByResidenceDetails(String residenceDetails, final String condominiumId) {
        final var residents = repository.findAllByHouseNumberAndCondominiumId(residenceDetails, condominiumId);

        if (residents.isEmpty()) {
            throw new ResidentNotFoundException("No residents found for the specified residence.");
        }
        return new ArrayList<>(residents);
    }

    @Override
    public CondominiumType getCondominiumType() {
        return CondominiumType.HORIZONTAL;
    }

}
