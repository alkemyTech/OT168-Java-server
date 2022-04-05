package com.alkemy.ong;

import com.alkemy.ong.data.entities.MemberEntity;
import com.alkemy.ong.data.repositories.MemberRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.web.controllers.MemberController;
import com.alkemy.ong.web.controllers.MemberController.MemberDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberRepository memberRepository;

    @Autowired
    ObjectMapper mapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void saveSuccess() throws Exception {

        MemberEntity entityRequest = toEntityTest();
        MemberEntity entityResponse = toEntityTest();
        MemberDTO memberDTO = toDTOTest();

        entityRequest.setId(null);

        when(memberRepository.save(entityRequest)).thenReturn(entityResponse);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name",is("James Potter")))
                .andExpect(jsonPath("$.facebookUrl",is("wwww.facebook/jamespotter.com")))
                .andExpect(jsonPath("$.instagramUrl",is("wwww.instagram/jamespotter.com")))
                .andExpect(jsonPath("$.linkedinUrl",is("wwww.linkedin/jamespotter.com")))
                .andExpect(jsonPath("$.image",is("james.jpg")))
                .andExpect(jsonPath("$.description",is("Some description about James Potter")))
                .andExpect(jsonPath("$.createdAt",is("2022-03-29 18:58:56")))
                .andExpect(jsonPath("$.updatedAt",is("2022-03-29 18:58:56")));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void saveBadRequest() throws Exception {

        MemberEntity entityRequest = toEntityTest();
        MemberDTO memberDTO = toDTOTest();

        entityRequest.setId(null);
        entityRequest.setName(null);

        memberDTO.setName(null);

        when(memberRepository.save(entityRequest)).thenReturn(null);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteSuccess() throws Exception {

        MemberEntity entity = toEntityTest();

        when(memberRepository.findById(entity.getId())).thenReturn(Optional.of(entity));

        mockMvc.perform(delete("/members/{id}",1))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteNotFound() throws Exception {

        when(memberRepository.findById(22l)).thenThrow(new ResourceNotFoundException(22l,"Member"));

        mockMvc.perform(delete("/members/{id}",22))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateSuccess() throws Exception {

        MemberEntity entity = toEntityTest();
        MemberDTO memberDTO = toDTOTest();

        memberDTO.setId(1l);

        when(memberRepository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(memberRepository.save(entity)).thenReturn(entity);

        mockMvc.perform(put("/members/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name",is("James Potter")))
                .andExpect(jsonPath("$.facebookUrl",is("wwww.facebook/jamespotter.com")))
                .andExpect(jsonPath("$.instagramUrl",is("wwww.instagram/jamespotter.com")))
                .andExpect(jsonPath("$.linkedinUrl",is("wwww.linkedin/jamespotter.com")))
                .andExpect(jsonPath("$.image",is("james.jpg")))
                .andExpect(jsonPath("$.description",is("Some description about James Potter")))
                .andExpect(jsonPath("$.createdAt",is("2022-03-29 18:58:56")))
                .andExpect(jsonPath("$.updatedAt",is("2022-03-29 18:58:56")));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateNotFound() throws Exception {
        MemberDTO memberDTO = toDTOTest();

        when(memberRepository.findById(22l)).thenThrow(new ResourceNotFoundException(22l,"Member"));

        memberDTO.setId(22l);

        mockMvc.perform(put("/members/{id}",22)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateBadRequest() throws Exception {

        MemberEntity memberEntity = toEntityTest();
        MemberDTO memberDTO = toDTOTest();

        memberEntity.setName(null);

        memberDTO.setId(1l);
        memberDTO.setName(null);

        when(memberRepository.findById(memberEntity.getId())).thenReturn(Optional.of(memberEntity));
        when(memberRepository.save(memberEntity)).thenReturn(null);

        mockMvc.perform(put("/members/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberDTO)))
                .andExpect(status().isBadRequest());
    }

    private MemberEntity toEntityTest(){
        return MemberEntity.builder()
                .id(1l)
                .name("James Potter")
                .facebookUrl("wwww.facebook/jamespotter.com")
                .instagramUrl("wwww.instagram/jamespotter.com")
                .linkedinUrl("wwww.linkedin/jamespotter.com")
                .image("james.jpg")
                .description("Some description about James Potter")
                .createdAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .updatedAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .build();
    }

    private MemberDTO toDTOTest(){
        return MemberDTO.builder()
                .name("James Potter")
                .facebookUrl("wwww.facebook/jamespotter.com")
                .instagramUrl("wwww.instagram/jamespotter.com")
                .linkedinUrl("wwww.linkedin/jamespotter.com")
                .image("james.jpg")
                .description("Some description about James Potter")
                .createdAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .updatedAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .build();
    }
}
