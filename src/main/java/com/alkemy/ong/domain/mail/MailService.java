package com.alkemy.ong.domain.mail;

import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final MailGateway mailGateway;

    public MailService(MailGateway mailGateway) {
        this.mailGateway = mailGateway;
    }

    public String sendMail(MailRequest mailRequest){
        return mailGateway.sendMail(mailRequest);
    }

    public String sendMailWithTemplate(String to, String subject, String body) {
        return mailGateway.sendMailWithTemplate(to, subject, body);
    }
}
