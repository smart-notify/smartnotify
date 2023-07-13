package com.smartnotify.resident.service;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.config.exception.ResidentNotFoundException;
import com.smartnotify.residence.factory.ResidenceDataNormalizerFactory;
import com.smartnotify.residence.normalizer.VerticalResidenceDataNormalizer;
import com.smartnotify.resident.api.json.RegisterVerticalCondoResidentRequest;
import com.smartnotify.resident.model.Resident;
import com.smartnotify.resident.model.VerticalCondoResident;
import com.smartnotify.resident.repository.VerticalCondoResidentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerticalCondoResidentServiceTest {

    @Mock
    private ResidenceDataNormalizerFactory normalizer;

    @Mock
    private VerticalResidenceDataNormalizer verticalResidenceDataNormalizer;

    @Mock
    private VerticalCondoResidentRepository repository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private VerticalCondoResidentService residentService;

    @Test
    void testBuildVerticalCondoResidentWithBlock() {
        final var request = RegisterVerticalCondoResidentRequest.builder()
                .name("Resident name")
                .email("resident@email.com")
                .apartmentNumber("APTO 32")
                .block("BLOCO A")
                .build();

        final var principal = Condominium.builder()
                .type(CondominiumType.VERTICAL)
                .build();

        when(authentication.getPrincipal()).thenReturn(principal);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(normalizer.findStrategy(CondominiumType.VERTICAL))
                .thenReturn(verticalResidenceDataNormalizer);

        when(verticalResidenceDataNormalizer.normalize(request.getApartmentNumber()))
                .thenReturn(request.getApartmentNumber());

        when(verticalResidenceDataNormalizer.normalize(request.getBlock()))
                .thenReturn(request.getBlock());

        VerticalCondoResident result = residentService.buildVerticalCondoResident(request);

        assertEquals("Resident name", result.getName());
        assertEquals("resident@email.com", result.getEmail());
        assertEquals("APTO 32", result.getApartmentNumber());
        assertEquals("BLOCO A", result.getBlock());
    }

    @Test
    void testBuildVerticalCondoResidentWithoutBlock() {
        final var request = RegisterVerticalCondoResidentRequest.builder()
                .name("Resident name")
                .email("resident@email.com")
                .apartmentNumber("APTO 32")
                .build();

        final var principal = Condominium.builder()
                .type(CondominiumType.VERTICAL)
                .build();

        when(authentication.getPrincipal()).thenReturn(principal);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(normalizer.findStrategy(CondominiumType.VERTICAL))
                .thenReturn(verticalResidenceDataNormalizer);

        when(verticalResidenceDataNormalizer.normalize(request.getApartmentNumber()))
                .thenReturn(request.getApartmentNumber());

        VerticalCondoResident result = residentService.buildVerticalCondoResident(request);

        assertEquals("Resident name", result.getName());
        assertEquals("resident@email.com", result.getEmail());
        assertEquals("APTO 32", result.getApartmentNumber());
        assertNull(result.getBlock());
    }

    @Test
    void testFindResidentByResidenceDetailsWithBlock() {
        final String residenceDetails = "APTO 22 BLOCO 1";
        final String condominiumId = "condominiumId";

        final var resident = new VerticalCondoResident();
        when(repository.findByApartmentNumberAndBlockAndCondominiumId(
                "APTO 22", "BLOCO 1", condominiumId))
                .thenReturn(Optional.of(resident));

        final Resident retrievedResident = residentService
                .findResidentByResidenceDetails(residenceDetails, condominiumId);

        assertEquals(resident, retrievedResident);
    }

    @Test
    void testFindResidentByResidenceDetailsWithoutBlock() {
        final String residenceDetails = "APTO 22";
        final String condominiumId = "condominiumId";

        final var resident = new VerticalCondoResident();
        when(repository
                .findByApartmentNumberAndBlockAndCondominiumId("APTO 22", null, condominiumId))
                .thenReturn(Optional.of(resident));

        final Resident retrievedResident = residentService
                .findResidentByResidenceDetails(residenceDetails, condominiumId);

        assertEquals(resident, retrievedResident);
    }

    @Test
    void testFindResidentByResidenceDetailsWithUnknownResidence() {
        String residenceDetails = "Unknown Apartment";
        String condominiumId = "condominiumId";
        when(repository
                .findByApartmentNumberAndBlockAndCondominiumId(
                        "Unknown Apartment", null, condominiumId))
                .thenReturn(Optional.empty());

        assertThrows(ResidentNotFoundException.class,
                () -> residentService.findResidentByResidenceDetails(residenceDetails, condominiumId));
    }

    @Test
    void testGetCondominiumType() {
        final var condominiumType = residentService.getCondominiumType();
        assertEquals(CondominiumType.VERTICAL, condominiumType);
    }

}
