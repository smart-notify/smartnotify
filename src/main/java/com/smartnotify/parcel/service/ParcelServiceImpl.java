package com.smartnotify.parcel.service;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.config.exception.ParcelNotFoundException;
import com.smartnotify.config.exception.SendParcelNotificationException;
import com.smartnotify.label.service.LabelService;
import com.smartnotify.notification.mail.MailService;
import com.smartnotify.notification.model.Notification;
import com.smartnotify.notification.repository.NotificationRepository;
import com.smartnotify.parcel.model.DeliveryStatus;
import com.smartnotify.parcel.model.Parcel;
import com.smartnotify.parcel.repository.ParcelRepository;
import com.smartnotify.resident.model.Resident;
import com.smartnotify.resident.service.factory.ResidentServiceFactory;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class ParcelServiceImpl implements ParcelService {

    private final LabelService labelService;
    private final MailService mailService;
    private final ParcelRepository parcelRepository;
    private final NotificationRepository notificationRepository;
    private final ResidentServiceFactory residentServiceFactory;

    @Override
    public List<Parcel> getParcelsByStatus(final DeliveryStatus status) {
        final var condominium = Condominium.getAuthenticatedCondominium();
        return parcelRepository.findAllByStatusAndCondominiumId(status, condominium.getId());
    }

    @Override
    @Transactional
    public void sendParcelNotification(final String labelContent) {
        final var residenceDetails = labelService.matchResidencePatternAndNormalize(labelContent);
        final List<Resident> residents = getResidentsByResidenceDetails(residenceDetails);

        try {
            final var parcel = parcelRepository.save(buildParcel(residenceDetails));
            notificationRepository.save(buildNotification(parcel));

            residents.forEach(resident ->
                    mailService.sendEmail(resident, parcel.getRegistrationCode(), parcel.getDeliveryCode())
            );

        } catch (RuntimeException e) {
            throw new SendParcelNotificationException("Error sending parcel notification. " + e);
        }
    }

    @Override
    @Transactional
    public void deliverParcel(final String id, final String deliveryCode) {
        final var condominium = Condominium.getAuthenticatedCondominium();

        final var affectedRows = parcelRepository.deliverParcel(id, deliveryCode, condominium.getId());
        if (affectedRows == 0) {
            throw new ParcelNotFoundException("Parcel not found");
        }
    }

    private List<Resident> getResidentsByResidenceDetails(final String residenceDetails) {
        final var condominium = Condominium.getAuthenticatedCondominium();

        return residentServiceFactory
                .findStrategy(condominium.getType())
                .findResidentsByResidenceDetails(residenceDetails, condominium.getId());
    }

    private static Notification buildNotification(final Parcel parcel) {
        return Notification.builder()
                .parcel(parcel)
                .build();
    }

    private Parcel buildParcel(final String residenceDetails) {
        return Parcel.builder()
                .status(DeliveryStatus.NOT_DELIVERED)
                .deliveryCode(generateFourDigitRandomNumber())
                .registrationCode(generateFourDigitRandomNumber())
                .residenceDetails(residenceDetails)
                .condominium(Condominium.getAuthenticatedCondominium())
                .build();
    }

    private String generateFourDigitRandomNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        byte accountLength = 4;

        for (int i = 0; i < accountLength; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

}
