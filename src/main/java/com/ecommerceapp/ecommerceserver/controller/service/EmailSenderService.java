package com.ecommerceapp.ecommerceserver.controller.service;

import com.ecommerceapp.ecommerceserver.controller.RegistrationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    private JavaMailSender javaMailSender;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendConfirmationMail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(RegistrationConstants.SUBJECT);
        message.setText(RegistrationConstants.MESSAGE_TEXT
                + RegistrationConstants.CONFIRMATION_ENDPOINT + token);
        try {
            javaMailSender.send(message);
        }
        catch (MailException e) {
            e.printStackTrace();
        }
    }
}
