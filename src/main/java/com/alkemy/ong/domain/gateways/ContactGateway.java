package com.alkemy.ong.domain.gateways;

import com.alkemy.ong.domain.models.Contact;

import java.util.List;

public interface ContactGateway {

  List<Contact> findAll();
  Contact save(Contact contact);
}
