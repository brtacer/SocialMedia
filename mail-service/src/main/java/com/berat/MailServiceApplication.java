package com.berat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class MailServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(MailServiceApplication.class);
    }
    // DENEME AMAÇLI
//    public MailServiceApplication(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//
//    private final JavaMailSender javaMailSender;
//    @EventListener(ApplicationReadyEvent.class)
//    public void sendMail(){
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setFrom("${mailUsername}");
//        mailMessage.setTo("cagri-acer2002@hotmail.com");
//        mailMessage.setSubject("Aktivasyon kodunuz..");
//        mailMessage.setText("Asd23");
//        javaMailSender.send(mailMessage);
//    }
}