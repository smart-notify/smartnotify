package com.smartnotify.resident.usecases.delete;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.config.exception.ResidentNotFoundException;
import com.smartnotify.parcel.model.DeliveryStatus;
import com.smartnotify.parcel.model.Parcel;
import com.smartnotify.parcel.repository.ParcelRepository;
import com.smartnotify.resident.model.HorizontalCondoResident;
import com.smartnotify.resident.repository.HorizontalCondoResidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteHorizontalCondoResidentTest {

    @Mock
    private HorizontalCondoResidentRepository residentRepository;

    @Mock
    private ParcelRepository parcelRepository;

    @InjectMocks
    private DeleteHorizontalCondoResident deleteResident;

    private HorizontalCondoResident resident;

    @BeforeEach
    void setup() {
        resident = new HorizontalCondoResident();
        resident.setEmail("email@email.com");
        resident.setCondominium(
                Condominium.builder()
                        .id("condominiumId")
                        .build());
    }

    @Test
    void testDeleteResident() {
        resident.setHouseNumber("CASA 1");

        List<Parcel> parcels = new ArrayList<>();
        parcels.add(Parcel.builder()
                .residenceDetails("CASA 1")
                .status(DeliveryStatus.NOT_DELIVERED).build());

        when(parcelRepository
                .findAllByResidenceDetailsAndCondominiumId("CASA 1", "condominiumId"))
                .thenReturn(parcels);

        when(residentRepository.findByEmailAndCondominiumId(resident.getEmail(), "condominiumId"))
                .thenReturn(Optional.of(resident));

        deleteResident.execute(resident.getEmail(), resident.getCondominium().getId());

        verify(residentRepository, times(1))
                .findByEmailAndCondominiumId(resident.getEmail(), "condominiumId");

        verify(parcelRepository, times(1))
                .findAllByResidenceDetailsAndCondominiumId("CASA 1", "condominiumId");

        verify(residentRepository, times(1))
                .countByHouseNumberAndCondominiumId(
                        "CASA 1",
                        "condominiumId");

        verify(residentRepository, times(1)).deleteByEmailAndCondominiumId(resident.getEmail(),
                resident.getCondominium().getId());
    }

    @Test
    void testDeleteNotFoundResident() {
        when(residentRepository.findByEmailAndCondominiumId(resident.getEmail(), "condominiumId"))
                .thenReturn(Optional.empty());

        final var exception = assertThrows(ResidentNotFoundException.class,
                () -> deleteResident.execute(resident.getEmail(), resident.getCondominium().getId()));

        assertEquals("Resident not found", exception.getMessage());
    }

    @Test
    void testGetCondominiumType() {
        assertEquals(CondominiumType.HORIZONTAL, deleteResident.getCondominiumType());
    }

}