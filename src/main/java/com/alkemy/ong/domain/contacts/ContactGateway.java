package com.alkemy.ong.domain.contacts;

import java.util.List;

public interface ContactGateway {

  List<Contact> findAll();
  Contact save(Contact contact);

}
