package com.smartnotify.resident.service;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.config.exception.ResidentNotFoundException;
import com.smartnotify.residence.factory.ResidenceDataNormalizerFactory;
import com.smartnotify.resident.api.json.RegisterVerticalCondoResidentRequest;
import com.smartnotify.resident.model.Resident;
import com.smartnotify.resident.model.VerticalCondoResident;
import com.smartnotify.resident.repository.VerticalCondoResidentRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class VerticalCondoResidentService implements ResidentService {

    private final ResidenceDataNormalizerFactory normalizer;
    private final VerticalCondoResidentRepository repository;

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

    @Override
    public List<Resident> findResidentsByResidenceDetails(final String residenceDetails, final String condominiumId) {
        if (isBlockPresent(residenceDetails)) {
            final var residenceParts = splitApartmentNumberAndBlock(residenceDetails);
            return new ArrayList<>(findApartmentResidentWithBlock(condominiumId, residenceParts));
        }
        return new ArrayList<>(findApartmentResidentWithoutBlock(residenceDetails, condominiumId));
    }

    @Override
    public CondominiumType getCondominiumType() {
        return CondominiumType.VERTICAL;
    }

    private List<VerticalCondoResident> findApartmentResidentWithBlock(final String condominiumId,
                                                                       final ApartmentBlockDetails parts) {
        final var residents = repository.findAllByApartmentNumberAndBlockAndCondominiumId(
                parts.getApartmentNumber(),
                parts.getBlock(),
                condominiumId);

        if (residents.isEmpty()) {
            throw new ResidentNotFoundException("No residents found for the specified residence.");
        }
        return residents;
    }

    private List<VerticalCondoResident> findApartmentResidentWithoutBlock(final String residenceDetails,
                                                                          final String condominiumId) {
        final var residents = repository
                .findAllByApartmentNumberAndBlockAndCondominiumId(residenceDetails, null, condominiumId);

        if (residents.isEmpty()) {
            throw new ResidentNotFoundException("No residents found for the specified residence.");
        }
        return residents;
    }

    private boolean isBlockPresent(final String residenceDetails) {
        long spaceCount = residenceDetails.chars().filter(c -> c == (int) ' ').count();
        return spaceCount > 1;
    }

    private ApartmentBlockDetails splitApartmentNumberAndBlock(final String residenceDetails) {
        String[] parts = residenceDetails.split("\\s+(?=BLOCO)");
        String apartmentNumber = parts[0];
        String block = parts[1];
        return new ApartmentBlockDetails(apartmentNumber, block);
    }

    @Getter
    @AllArgsConstructor
    private static class ApartmentBlockDetails {
        private String apartmentNumber;
        private String block;
    }

}
