package com.alkemy.ong;

import com.alkemy.ong.data.entities.MemberEntity;
import com.alkemy.ong.data.repositories.MemberRepository;
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
import java.util.Objects;


import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        ArgumentCaptor<MemberEntity> argumentCaptor = ArgumentCaptor.forClass(MemberEntity.class);
        verify(memberRepository,times(1)).save(argumentCaptor.capture());
        verifyNoMoreInteractions(memberRepository);
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
