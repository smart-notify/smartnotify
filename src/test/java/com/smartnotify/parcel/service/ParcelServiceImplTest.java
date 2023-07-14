package com.smartnotify.parcel.service;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.config.exception.ParcelNotFoundException;
import com.smartnotify.label.service.LabelService;
import com.smartnotify.notification.mail.MailService;
import com.smartnotify.notification.repository.NotificationRepository;
import com.smartnotify.parcel.model.DeliveryStatus;
import com.smartnotify.parcel.model.Parcel;
import com.smartnotify.parcel.repository.ParcelRepository;
import com.smartnotify.resident.model.Resident;
import com.smartnotify.resident.model.VerticalCondoResident;
import com.smartnotify.resident.service.VerticalCondoResidentService;
import com.smartnotify.resident.service.factory.ResidentServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParcelServiceImplTest {

    @Mock
    private LabelService labelService;

    @Mock
    private MailService mailService;

    @Mock
    private ParcelRepository parcelRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private ResidentServiceFactory residentServiceFactory;

    @Mock
    private VerticalCondoResidentService verticalCondoResidentService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private ParcelServiceImpl parcelService;

    private Condominium condominium;

    @BeforeEach
    void setup() {
        condominium = Condominium.builder()
                .id("condominiumId")
                .type(CondominiumType.VERTICAL)
                .build();

        when(authentication.getPrincipal()).thenReturn(condominium);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getParcelsByStatus() {
        final var status = DeliveryStatus.NOT_DELIVERED;

        final List<Parcel> expectedParcels = new ArrayList<>();
        when(parcelRepository.findAllByStatusAndCondominiumId(status, condominium.getId()))
                .thenReturn(expectedParcels);

        final List<Parcel> result = parcelService.getParcelsByStatus(status);

        assertEquals(expectedParcels, result);
        verify(parcelRepository, times(1))
                .findAllByStatusAndCondominiumId(status, condominium.getId());
    }

    @Test
    void testSendParcelNotification() {
        final String labelContent = "Label Content";
        final String residenceDetails = "Residence Details";
        final Resident resident = new VerticalCondoResident();
        final Parcel parcel = new Parcel();

        when(labelService.matchResidencePatternAndNormalize(labelContent)).thenReturn(residenceDetails);
        when(residentServiceFactory.findStrategy(CondominiumType.VERTICAL))
                .thenReturn(verticalCondoResidentService);
        when(parcelRepository.save(any())).thenReturn(parcel);

        assertDoesNotThrow(() -> parcelService.sendParcelNotification(labelContent));

        verify(labelService, times(1)).matchResidencePatternAndNormalize(labelContent);
        verify(residentServiceFactory, times(1)).findStrategy(any());
        verify(mailService, times(1)).sendEmail(any(), any(), any());
        verify(parcelRepository, times(1)).save(any());
        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    void deliverParcel_ValidIdAndDeliveryCode_DeliversParcel() {
        final String id = "parcelId";
        final String deliveryCode = "1234";
        final Integer numberOfAffectedRows = 1;

        when(parcelRepository.deliverParcel(id, deliveryCode, condominium.getId())).thenReturn(numberOfAffectedRows);

        assertDoesNotThrow(() -> parcelService.deliverParcel(id, deliveryCode));

        verify(parcelRepository, times(1)).deliverParcel(id, deliveryCode, condominium.getId());
    }

    @Test
    void testDeliverParcelNotFound() {
        final String id = "parcelId";
        final String deliveryCode = "1234";
        final Integer numberOfAffectedRows = 0;

        when(parcelRepository.deliverParcel(id, deliveryCode, condominium.getId())).thenReturn(numberOfAffectedRows);

        final var exception = assertThrows(ParcelNotFoundException.class,
                () -> parcelService.deliverParcel(id, deliveryCode));

        assertEquals("Parcel not found", exception.getMessage());

        verify(parcelRepository, times(1)).deliverParcel(id, deliveryCode, condominium.getId());
    }

}
