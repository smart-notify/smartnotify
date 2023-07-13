package com.smartnotify.notification.mail;

import com.smartnotify.resident.model.Resident;

public interface MailService {

    void sendEmail(Resident resident, String registrationCode, String deliveryCode);

}
