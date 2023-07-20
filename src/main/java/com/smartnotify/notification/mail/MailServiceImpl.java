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
                .append("Chegou uma encomenda para sua residência!")
                .append("\n\n")
                .append("> Código de identificação: ")
                .append(registrationCode)
                .append("\n")
                .append("> Código de retirada: ")
                .append(deliveryCode)
                .append("\n\n")
                .append("Para garantir a segurança e integridade da entrega, ")
                .append("é necessário informar os dois códigos listados acima no momento da retirada da encomenda.")
                .append("\n\n")
                .append(condominium.getName())
                .append(" - Smartnotify group");

        mail.setSubject("Smartnotify - Chegou uma encomenda!");
        mail.setText(message.toString());

        javaMailSender.send(mail);
    }

}
