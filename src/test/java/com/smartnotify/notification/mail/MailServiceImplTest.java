package com.smartnotify.notification.mail;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.resident.model.VerticalCondoResident;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MailServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private MailServiceImpl mailService;

    @Test
    void testSendMail() {
        final var condominium = Condominium.builder()
                .id("condominiumId")
                .type(CondominiumType.VERTICAL)
                .build();

        when(authentication.getPrincipal()).thenReturn(condominium);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        final var resident = new VerticalCondoResident();
        resident.setName("name");
        resident.setEmail("email@email.com");

        final String deliveryCode = "1234";
        final String registrationCode = "4567";

        mailService.sendEmail(resident, deliveryCode, registrationCode);

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

}
