package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.ContactEntity;
import com.alkemy.ong.data.repositories.ContactRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import static com.alkemy.ong.web.controllers.ContactController.ContactDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ContactRepository contactRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        List<ContactEntity> contacts = Arrays.asList(
                createContact(1L, "Juan Perez", "342525156", "juanperez@gmail.com", "MessageExample" ),
                createContact(2L, "Ignacio Rodriguez", "11334565", "ignacior@gmail.com", "MessageExample2" ));
        when(contactRepository.findAll()).thenReturn(contacts);

        ContactEntity contact = createContact(null, "Maria Gonzalez", "341232456", "mariagonzalez@gmail.com", "MessageExample3" );
        ContactEntity contactSaved = createContact(1L, "Maria Gonzalez", "341232456", "mariagonzalez@gmail.com", "MessageExample3" );
        when(contactRepository.save(contact)).thenReturn(contactSaved);
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllContacts() throws Exception {
        mockMvc.perform(get("/contacts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Juan Perez"))
                .andExpect(jsonPath("$[0].phone").value("342525156"))
                .andExpect(jsonPath("$[0].email").value("juanperez@gmail.com"))
                .andExpect(jsonPath("$[0].message").value("MessageExample"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Ignacio Rodriguez"))
                .andExpect(jsonPath("$[1].phone").value("11334565"))
                .andExpect(jsonPath("$[1].email").value("ignacior@gmail.com"))
                .andExpect(jsonPath("$[1].message").value("MessageExample2"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllContactsUser() throws Exception {
        mockMvc.perform(get("/contacts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void saveContactOk() throws Exception {
        ContactDTO contactDTO = createContactDTO("Maria Gonzalez", "341232456", "mariagonzalez@gmail.com", "MessageExample3");

        mockMvc.perform(post("/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", is("341232456")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is("mariagonzalez@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("MessageExample3")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void saveContactBadRequest() throws Exception {
        ContactDTO contactDTO = createContactDTO("", "341232456", "", "MessageExample3");

        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDTO)))
                .andExpect(status().isBadRequest());
    }

    private ContactEntity createContact(Long id, String name, String phone, String email, String message){
        return ContactEntity.builder()
                .id(id)
                .name(name)
                .email(email)
                .message(message)
                .phone(phone)
                .build();
    }

    private ContactDTO createContactDTO(String name, String phone, String email, String message){
        return ContactDTO.builder()
                .name(name)
                .email(email)
                .message(message)
                .phone(phone)
                .build();
    }
}