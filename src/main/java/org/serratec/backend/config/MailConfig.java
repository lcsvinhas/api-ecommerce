package org.serratec.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailConfig {
    @Autowired
    private JavaMailSender mailSender;

    public void enviar(String para, String assunto, String texto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("${mail_username}");
        message.setTo(para);
        message.setSubject(assunto);
        message.setText("Sua conta foi criada/alterada!");
        mailSender.send(message);
    }
}
