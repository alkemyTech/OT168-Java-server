package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.ContactEntity;
import com.alkemy.ong.data.repositories.ContactRepository;
import com.alkemy.ong.domain.contacts.ContactGateway;
import com.alkemy.ong.domain.contacts.Contact;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultContactGateway implements ContactGateway {

    private final ContactRepository contactRepository;

    public DefaultContactGateway(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll().stream()
                .map(this::toModel)
                .collect(toList());
    }

    @Override
    public Contact save(Contact contact) {
        return toModel(contactRepository.save(toEntity(contact)));
    }

    private Contact toModel(ContactEntity contactEntity){
        return Contact.builder()
                .id(contactEntity.getId())
                .name(contactEntity.getName())
                .phone(contactEntity.getPhone())
                .email(contactEntity.getEmail())
                .message(contactEntity.getMessage())
                .createdAt(contactEntity.getCreatedAt())
                .updatedAt(contactEntity.getUpdatedAt())
                .build();
    }

    private ContactEntity toEntity(Contact contact){
        return ContactEntity.builder()
                .id(contact.getId())
                .name(contact.getName())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .message(contact.getMessage())
                .build();
    }
}
