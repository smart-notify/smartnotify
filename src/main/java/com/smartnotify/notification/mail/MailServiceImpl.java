package com.smartnotify.notification.mail;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.resident.model.Resident;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(final Resident resident, final String registrationCode, final String deliveryCode) {
        final SimpleMailMessage mail = new SimpleMailMessage();
        final var condominium = Condominium.getAuthenticatedCondominium();

        mail.setFrom(condominium.getEmail());
        mail.setTo(resident.getEmail());

        StringBuilder message = new StringBuilder();
        message.append("Olá, ")
                .append(resident.getName())
                .append("!")
                .append("\n\n")
                .append("Sua encomenda chegou!")
                .append("\n\n")
                .append("> Código de identificação: ")
                .append(registrationCode)
                .append("\n")
                .append("> Código de retirada: ")
                .append(deliveryCode)
                .append("\n\n")
                .append("Para garantir a segurança e integridade de sua entrega, ")
                .append("é necessário informar os dois códigos listados acima no momento da retirada de sua encomenda.")
                .append("\n\n")
                .append(condominium.getName())
                .append(" - Smartnotify group");

        mail.setSubject("Smartnotify - Sua encomenda chegou!");
        mail.setText(message.toString());

        javaMailSender.send(mail);
    }

}
