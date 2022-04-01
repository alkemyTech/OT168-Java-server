package com.alkemy.ong.domain.mail;


import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailService {

    private final MailGateway mailGateway;

    public MailService(MailGateway mailGateway) {
        this.mailGateway = mailGateway;
    }

    public String sendMail(MailRequest mailRequest) throws IOException {
        return mailGateway.sendMail(mailRequest);
    }
}
