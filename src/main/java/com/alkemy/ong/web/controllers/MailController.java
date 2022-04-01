package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.mail.MailRequest;
import com.alkemy.ong.domain.mail.MailService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.IOException;

@RestController
@RequestMapping("mail/")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/sendmail")
    public ResponseEntity<String> sendMail(@RequestBody @Valid MailDTO mailDTO) throws IOException {
        String email = mailService.sendMail(toModel(mailDTO));
        return ResponseEntity.ok("Email enviado exitosamente a: " + mailDTO.getTo());
    }

    private MailRequest toModel(MailDTO dto){
        return MailRequest.builder()
                .to(dto.getTo())
                .subject(dto.getSubject())
                .body(dto.getBody())
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
