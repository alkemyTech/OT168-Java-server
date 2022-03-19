package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.ContactEntity;
import com.alkemy.ong.data.repositories.ContactRepository;
import com.alkemy.ong.domain.contact.ContactGateway;
import com.alkemy.ong.domain.contact.Contact;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultContactGateway implements ContactGateway {

    private final ContactRepository contactRepository;

    public DefaultContactGateway(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> findAll() {
        List<Contact> models;

        models = contactRepository.findAll().stream()
                .map(c -> toModel(c))
                .collect(toList());
        return models;
    }

    @Override
    public Contact save(Contact contact) {
        ContactEntity contactEntity = toEntity(contact);
        contactRepository.save(contactEntity);
        return contact;
    }

    private Contact toModel(ContactEntity contactEntity){
        Contact c = Contact.builder()
                .id(contactEntity.getId())
                .name(contactEntity.getName())
                .phone(contactEntity.getPhone())
                .email(contactEntity.getEmail())
                .message(contactEntity.getMessage())
                .createdAt(contactEntity.getCreatedAt())
                .updatedAt(contactEntity.getUpdatedAt())
                .deleted(contactEntity.getDeleted())
                .build();
        return c;
    }

    private ContactEntity toEntity(Contact contact){
        ContactEntity contactEntity = ContactEntity.builder()
                .id(contact.getId())
                .name(contact.getName())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .message(contact.getMessage())
                .createdAt(contact.getCreatedAt())
                .updatedAt(contact.getUpdatedAt())
                .deleted(contact.getDeleted())
                .build();
        return contactEntity;
    }
}
