package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.mail.MailRequest;
import com.alkemy.ong.domain.mail.MailService;
import com.sendgrid.helpers.mail.Mail;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Tag(name = "Mail")
@RestController
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/sendmail")
    public ResponseEntity<String> sendMailWithTemplate(@RequestParam("to") String to, @RequestParam("subject") String subject, @RequestParam("body") String body){
        String email = mailService.sendMailWithTemplate(to, subject, body);
        return ResponseEntity.ok("Email successfully sent");
    }

    private MailRequest toModel(MailDTO mailDTO) {
        return MailRequest.builder()
                .to(mailDTO.getTo())
                .subject(mailDTO.getSubject())
                .body(mailDTO.getBody())
                .build();
    }

    @Data
    @Builder
    public static class MailDTO{
        @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
                "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
                message = "Error: invalid email address, please check format")
        private String to;
        private String subject;
        private String body;
    }
}