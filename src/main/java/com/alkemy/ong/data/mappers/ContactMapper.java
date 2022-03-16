package com.alkemy.ong.data.mappers;

import com.alkemy.ong.data.entities.Contact;
import org.mapstruct.Mapper;

@Mapper
public interface ContactMapper {
    Contact toContact(Contact contact);
}
