package com.berat.service;

import com.berat.rabbitmq.model.RegisterMailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender javaMailSender;

    public void sendMail(RegisterMailModel model){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("${mailUsername}");
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("Aktivasyon kodunuz..");
        mailMessage.setText(model.getUsername()+"adıyla başarıyla kaydedildi\n"
                +"Aktivasyon kodunuz : "+model.getActivationCode());
        javaMailSender.send(mailMessage);
    }
}
