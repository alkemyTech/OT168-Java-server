package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.contact.Contact;
import com.alkemy.ong.domain.contact.ContactService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

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
        List<ContactDTO> dto;
           dto = contactService.getContacts().stream()
                    .map(c -> toDto(c))
                    .collect(toList());
        return ResponseEntity.ok(dto);
    }

    private ContactDTO toDto (Contact contact){
        ContactDTO c = ContactDTO.builder()
                .id(contact.getId())
                .name(contact.getName())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .message(contact.getMessage())
                .createdAt(contact.getCreatedAt())
                .updatedAt(contact.getUpdatedAt())
                .deleted(contact.getDeleted())
                .build();
        return c;
    }
}

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ContactDTO{
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean deleted;
}