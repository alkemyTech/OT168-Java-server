package com.alkemy.ong.domain.mail;

import java.io.IOException;

public interface MailGateway {
    String sendMail(MailRequest mailRequest) throws IOException;
}
