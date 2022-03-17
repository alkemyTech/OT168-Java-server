package com.alkemy.ong.domain.contact;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactGateway contactGateway;

    public ContactService(ContactGateway contactGateway){
        this.contactGateway = contactGateway;
    }

    public Contact saveContact(Contact contact) throws Exception {
        try {
            contactGateway.save(contact);
            return contact;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Contact> getContacts() throws Exception{
        try {
            return contactGateway.findAll();
        }catch (Exception e){
            throw e;
        }

    }
}
