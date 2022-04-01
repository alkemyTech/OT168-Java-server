package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.contacts.Contact;
import com.alkemy.ong.domain.contacts.ContactService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

import static java.net.URI.create;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @GetMapping()
    public ResponseEntity<List<ContactDTO>> getAllContacts() throws Exception {
        return ResponseEntity.ok(contactService.getContacts().stream()
                .map(this::toDTO)
                .collect(toList()));
    }

    @PostMapping
    public ResponseEntity<ContactDTO> saveContact(@Valid @RequestBody ContactDTO contactDTO){
        contactDTO = toDTO(contactService.saveContact(toModel(contactDTO)));
        return ResponseEntity.created(create("contacts/" + contactDTO.getId())).body(contactDTO);
    }

    private ContactDTO toDTO (Contact contact){
        return ContactDTO.builder()
                .id(contact.getId())
                .name(contact.getName())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .message(contact.getMessage())
                .createdAt(contact.getCreatedAt())
                .updatedAt(contact.getUpdatedAt())
                .build();
    }

    private Contact toModel(ContactDTO contactDTO){
        return Contact.builder()
                .name(contactDTO.getName())
                .phone(contactDTO.getPhone())
                .email(contactDTO.getEmail())
                .message(contactDTO.getMessage())
                .createdAt(contactDTO.getCreatedAt())
                .updatedAt(contactDTO.getUpdatedAt())
                .build();
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ContactDTO{
        private Long id;
        @NotEmpty(message = "Name can't be empty")
        private String name;
        private String phone;
        @NotEmpty(message = "Email can't be empty")
        @Email(message = "Invalid email")
        private String email;
        private String message;
        @JsonFormat(pattern="dd-MM-yyyy hh:mm")
        private LocalDateTime createdAt;
        @JsonFormat(pattern="dd-MM-yyyy hh:mm")
        private LocalDateTime updatedAt;
    }
}