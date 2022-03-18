package com.alkemy.ong.domain.contact;

import java.util.List;

public interface ContactGateway {

  List<Contact> findAll();
  Contact save(Contact contact);

}
