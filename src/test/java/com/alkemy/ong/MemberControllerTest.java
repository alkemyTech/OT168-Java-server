package com.alkemy.ong;

import com.alkemy.ong.data.entities.MemberEntity;
import com.alkemy.ong.data.repositories.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.Valid;
import java.time.LocalDateTime;


import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
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

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void save() throws Exception {

        MemberEntity entityRequest = toEntityTest();
        MemberEntity entityResponse = toEntityTest();
        MemberDTOTest dtoTest = new MemberDTOTest();

        entityRequest.setId(null);
        //entityRequest.setCreatedAt(null);
        //entityRequest.setUpdatedAt(null);

        when(memberRepository.save(entityRequest)).thenReturn(entityResponse);

        this.mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dtoTest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("James Potter")))
                .andExpect(jsonPath("$.facebookUrl",is("wwww.facebook/jamespotter.com")))
                .andExpect(jsonPath("$.instagramUrl",is("wwww.instagram/jamespotter.com")))
                .andExpect(jsonPath("$.linkedinUrl",is("wwww.linkedin/jamespotter.com")))
                .andExpect(jsonPath("$.image",is("james.jpg")))
                .andExpect(jsonPath("$.description",is("Some description about James Potter")))
                .andExpect(jsonPath("$.createdAt",is("2022-03-29 18:58:56")))
                .andExpect(jsonPath("$.updatedAt",is("2022-03-29 18:58:56")));

            /*
            "2022-03-29 18:58:56"
            this.mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dtoTest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",is(dtoTest.getId())))
                .andExpect(jsonPath("$.name",is(dtoTest.getName())))
                .andExpect(jsonPath("$.facebookUrl",is(dtoTest.getFacebookUrl())))
                .andExpect(jsonPath("$.instagramUrl",is(dtoTest.getInstagramUrl())))
                .andExpect(jsonPath("$.linkedinUrl",is(dtoTest.getLinkedinUrl())))
                .andExpect(jsonPath("$.image",is(dtoTest.getImage())))
                .andExpect(jsonPath("$.description",is(dtoTest.getDescription())))
                .andExpect(jsonPath("$.createdAt",is(dtoTest.getCreatedAt())))
                .andExpect(jsonPath("$.updatedAt",is(dtoTest.getUpdatedAt())));
     */

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
                //.createdAt(LocalDateTime.now())
                //.updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .updatedAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    @Valid
    private static class MemberDTOTest {

        @Builder.Default
        private Long id = 1l;
        @Builder.Default
        private String name = "James Potter";
        @Builder.Default
        private String facebookUrl = "wwww.facebook/jamespotter.com";
        @Builder.Default
        private String instagramUrl = "wwww.instagram/jamespotter.com";
        @Builder.Default
        private String linkedinUrl = "wwww.linkedin/jamespotter.com";
        @Builder.Default
        private String image = "james.jpg";
        @Builder.Default
        private String description = "Some description about James Potter";
        @Builder.Default//2022-03-29 18:58:56
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime createdAt = LocalDateTime.of(2022,03,29,18,58,56,555);
        @Builder.Default
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime updatedAt = LocalDateTime.of(2022,03,29,18,58,56,555);
    }
}
