package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.ContactEntity;
import com.alkemy.ong.data.repositories.ContactRepository;
import com.alkemy.ong.domain.contact.ContactGateway;
import com.alkemy.ong.domain.contact.Contact;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultContactGateway implements ContactGateway {

    private final ContactRepository contactRepository;

    public DefaultContactGateway(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> findAll() {
        List<Contact> models = new ArrayList();
        List<ContactEntity> contacts = contactRepository.findAll();

        if (!contacts.isEmpty()) {
            for (ContactEntity contact : contacts) {
                Contact c = Contact.builder()
                        .id(contact.getId())
                        .name(contact.getName())
                        .phone(contact.getPhone())
                        .email(contact.getEmail())
                        .message(contact.getMessage())
                        .createdAt(contact.getCreatedAt())
                        .updatedAt(contact.getUpdatedAt())
                        .deletedAt(contact.getDeletedAt())
                        .build();
                models.add(c);
            }
        }
        return models;
    }

    @Override
    public Contact save(Contact contact) {
        ContactEntity contactEntity = ContactEntity.builder()
                .id(contact.getId())
                .name(contact.getName())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .message(contact.getMessage())
                .createdAt(contact.getCreatedAt())
                .updatedAt(contact.getUpdatedAt())
                .deletedAt(contact.getDeletedAt())
                .build();
        contactRepository.save(contactEntity);
        return contact;
    }
}
