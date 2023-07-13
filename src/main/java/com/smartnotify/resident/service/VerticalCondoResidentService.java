package com.smartnotify.resident.service;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.residence.factory.ResidenceDataNormalizerFactory;
import com.smartnotify.resident.api.json.RegisterVerticalCondoResidentRequest;
import com.smartnotify.resident.model.VerticalCondoResident;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VerticalCondoResidentService {

    private final ResidenceDataNormalizerFactory normalizer;

    public VerticalCondoResident buildVerticalCondoResident(final RegisterVerticalCondoResidentRequest request) {
        final var strategy = normalizer.findStrategy(Condominium.getAuthenticatedCondominium().getType());

        final var apartmentNumber = strategy.normalize(request.getApartmentNumber());
        final var block = request.getBlock() != null ? strategy.normalize(request.getBlock()).trim() : null;

        final var resident = VerticalCondoResident.builder()
                .apartmentNumber(apartmentNumber)
                .block(block)
                .build();

        resident.setName(request.getName());
        resident.setEmail(request.getEmail());
        resident.setCondominium(Condominium.getAuthenticatedCondominium());

        return resident;
    }

}
