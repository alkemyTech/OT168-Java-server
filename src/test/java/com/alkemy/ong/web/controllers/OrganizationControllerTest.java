package com.alkemy.ong.web.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.alkemy.ong.data.entities.OrganizationEntity;
import com.alkemy.ong.data.repositories.OrganizationRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OrganizationRepository organizationRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getOrganizationByIdSuccessTest() throws Exception {
        OrganizationEntity organizationEntity = toEntity(1L, "Organization", "image.png", "Mendoza", 5492615111111L, "org@mail.com", "Welcome!", "About us", "fb.com/org", "lkdn.com/org", "instagram.com/org");

        when(organizationRepository.findById(organizationEntity.getIdOrganization())).thenReturn(Optional.of(organizationEntity));

        mockMvc.perform(get("/organizations/public/1")
                .contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.idOrganization").value(1))
                .andExpect(jsonPath("$.name").value("Organization"))
                .andExpect(jsonPath("$.image").value("image.png"))
                .andExpect(jsonPath("$.address").value("Mendoza"))
                .andExpect(jsonPath("$.phone").value(5492615111111L))
                .andExpect(jsonPath("$.email").value("org@mail.com"))
                .andExpect(jsonPath("$.welcomeText").value("Welcome!"))
                .andExpect(jsonPath("$.aboutUsText").value("About us"))
                .andExpect(jsonPath("$.facebookUrl").value("fb.com/org"))
                .andExpect(jsonPath("$.linkedinUrl").value("lkdn.com/org"))
                .andExpect(jsonPath("$.instagramUrl").value("instagram.com/org"));
    }

    @Test
    void getOrganizationByIdNotFoundTest() throws Exception {
        doThrow(ResourceNotFoundException.class).when(organizationRepository).findById(555L);

        mockMvc.perform(get("/organizations/public/555")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateOrganizationSuccessTest() throws Exception {
        OrganizationEntity updateOrganizationEntity = toEntity(1L, "Organization", "image.png", "Mendoza", 5492615111111L, "org@mail.com", "Welcome!", "About us", "fb.com/org", "lkdn.com/org", "instagram.com/org");

        when(organizationRepository.findById(updateOrganizationEntity.getIdOrganization())).thenReturn(Optional.of(updateOrganizationEntity));
        when(organizationRepository.save(updateOrganizationEntity)).thenReturn(updateOrganizationEntity);

        mockMvc.perform(put("/organizations/public/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateOrganizationEntity))).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.idOrganization").value(1))
                .andExpect(jsonPath("$.name").value("Organization"))
                .andExpect(jsonPath("$.image").value("image.png"))
                .andExpect(jsonPath("$.address").value("Mendoza"))
                .andExpect(jsonPath("$.phone").value(5492615111111L))
                .andExpect(jsonPath("$.email").value("org@mail.com"))
                .andExpect(jsonPath("$.welcomeText").value("Welcome!"))
                .andExpect(jsonPath("$.aboutUsText").value("About us"))
                .andExpect(jsonPath("$.facebookUrl").value("fb.com/org"))
                .andExpect(jsonPath("$.linkedinUrl").value("lkdn.com/org"))
                .andExpect(jsonPath("$.instagramUrl").value("instagram.com/org"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateOrganizationFailTest() throws Exception {
        OrganizationEntity updateOrganizationEntity = toEntity(1L, "Organization", "image.png", "Mendoza", 5492615111111L, "org@mail.com", "Welcome!", "About us", "fb.com/org", "lkdn.com/org", "instagram.com/org");

        when(organizationRepository.findById(updateOrganizationEntity.getIdOrganization())).thenReturn(Optional.of(updateOrganizationEntity));
        when(organizationRepository.save(updateOrganizationEntity)).thenReturn(updateOrganizationEntity);

        mockMvc.perform(put("/organizations/public/555")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateOrganizationEntity))).andExpect(status().isBadRequest());
    }

    private OrganizationEntity toEntity(Long id, String name, String image, String address, Long phone, String email, String welcomeText, String aboutUsText, String facebookUrl, String linkedinUrl, String instagramUrl) {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setIdOrganization(id);
        organizationEntity.setName(name);
        organizationEntity.setImage(image);
        organizationEntity.setAddress(address);
        organizationEntity.setPhone(phone);
        organizationEntity.setEmail(email);
        organizationEntity.setAboutUsText(aboutUsText);
        organizationEntity.setWelcomeText(welcomeText);
        organizationEntity.setFacebookUrl(facebookUrl);
        organizationEntity.setLinkedinUrl(linkedinUrl);
        organizationEntity.setInstagramUrl(instagramUrl);
        return organizationEntity;
    }
}
