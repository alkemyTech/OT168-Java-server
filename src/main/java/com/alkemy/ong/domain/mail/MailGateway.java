package com.alkemy.ong.domain.mail;

public interface MailGateway {
    String sendMail (MailRequest mailRequest);
    String sendMailWithTemplate(String to,String body);
}