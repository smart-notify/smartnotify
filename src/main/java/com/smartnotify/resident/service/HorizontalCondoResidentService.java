package com.smartnotify.resident.service;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.residence.factory.ResidenceDataNormalizerFactory;
import com.smartnotify.resident.api.json.RegisterHorizontalCondoResidentRequest;
import com.smartnotify.resident.model.HorizontalCondoResident;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HorizontalCondoResidentService {

    private final ResidenceDataNormalizerFactory normalizer;

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

}
