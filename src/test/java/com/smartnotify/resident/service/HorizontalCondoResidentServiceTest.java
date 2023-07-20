package com.smartnotify.resident.service;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.config.exception.ResidentNotFoundException;
import com.smartnotify.residence.factory.ResidenceDataNormalizerFactory;
import com.smartnotify.residence.normalizer.HorizontalResidenceDataNormalizer;
import com.smartnotify.resident.api.json.RegisterHorizontalCondoResidentRequest;
import com.smartnotify.resident.model.HorizontalCondoResident;
import com.smartnotify.resident.model.Resident;
import com.smartnotify.resident.repository.HorizontalCondoResidentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HorizontalCondoResidentServiceTest {

    @Mock
    private ResidenceDataNormalizerFactory normalizer;

    @Mock
    private HorizontalResidenceDataNormalizer horizontalResidenceDataNormalizer;

    @Mock
    private HorizontalCondoResidentRepository repository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private HorizontalCondoResidentService residentService;

    @Test
    void testBuildHorizontalCondoResident() {
        final var request = RegisterHorizontalCondoResidentRequest.builder()
                .name("Resident name")
                .email("resident@email.com")
                .houseNumber("CASA 32")
                .build();

        final var principal = Condominium.builder()
                .type(CondominiumType.HORIZONTAL)
                .build();

        when(authentication.getPrincipal()).thenReturn(principal);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(normalizer.findStrategy(CondominiumType.HORIZONTAL))
                .thenReturn(horizontalResidenceDataNormalizer);

        when(horizontalResidenceDataNormalizer.normalize(request.getHouseNumber()))
                .thenReturn(request.getHouseNumber());

        HorizontalCondoResident result = residentService.buildHorizontalCondoResident(request);

        assertEquals("Resident name", result.getName());
        assertEquals("resident@email.com", result.getEmail());
        assertEquals("CASA 32", result.getHouseNumber());
    }

    @Test
    void testFindResidentByResidenceDetails() {
        final String residenceDetails = "CASA 1";
        final String condominiumId = "condominiumId";

        final var resident = new HorizontalCondoResident();
        when(repository.findAllByHouseNumberAndCondominiumId(
                "CASA 1", condominiumId))
                .thenReturn(List.of(resident));

        final List<Resident> retrievedResidents = residentService
                .findResidentsByResidenceDetails(residenceDetails, condominiumId);

        assertEquals(resident, retrievedResidents.get(0));
    }

    @Test
    void testFindResidentByResidenceDetailsWithUnknownResidence() {
        String residenceDetails = "Unknown House";
        String condominiumId = "condominiumId";
        when(repository
                .findAllByHouseNumberAndCondominiumId(
                        "Unknown House", condominiumId))
                .thenReturn(List.of());

        assertThrows(ResidentNotFoundException.class,
                () -> residentService.findResidentsByResidenceDetails(residenceDetails, condominiumId));
    }

    @Test
    void testGetCondominiumType() {
        final var condominiumType = residentService.getCondominiumType();
        assertEquals(CondominiumType.HORIZONTAL, condominiumType);
    }

}
