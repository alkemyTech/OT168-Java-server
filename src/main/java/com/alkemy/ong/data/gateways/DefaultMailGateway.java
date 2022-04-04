package com.alkemy.ong.data.gateways;

import com.alkemy.ong.domain.exceptions.SendgridException;
import com.alkemy.ong.domain.mail.MailGateway;
import com.alkemy.ong.domain.mail.MailRequest;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DefaultMailGateway implements MailGateway {

    private final SendGrid sendgrid;

    public DefaultMailGateway(SendGrid sendgrid) {
        this.sendgrid = sendgrid;
    }

    @Override
    public String sendMail(MailRequest mailRequest){

        Email email = new Email(System.getenv("SENGRID_EMAIL"), "ONG - Somos Más");
        Mail mail = new Mail(email, mailRequest.getSubject(), new Email(mailRequest.getTo()), new Content("text/plain", mailRequest.getBody()));
        mail.setReplyTo(email);

        /**
         * IOException nunca se produce, revisar!!
         */

/*        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendgrid.api(request);
            return response.getBody();
        }catch (IOException ex){
            throw new SendgridException("ERROR building mail");
        }
    }
}