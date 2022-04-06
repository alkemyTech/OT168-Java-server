package com.alkemy.ong;

import com.alkemy.ong.data.entities.MemberEntity;
import com.alkemy.ong.data.pagination.PageModel;
import com.alkemy.ong.data.repositories.MemberRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.web.controllers.MemberController.MemberDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.alkemy.ong.data.utils.PaginationUtils.DEFAULT_PAGE_SIZE;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
                        .contentType(APPLICATION_JSON)
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
                        .contentType(APPLICATION_JSON)
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
                        .contentType(APPLICATION_JSON)
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
                        .contentType(APPLICATION_JSON)
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
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void findAllSuccess() throws Exception {
        PageModel<MemberEntity> pageModel = new PageModel<>();
        pageModel.setBody(toListEntity());
        pageModel.setNextPage("This is the last page");
        pageModel.setPreviousPage("This is the first page");

        when(memberRepository.findAll(PageRequest.of(0,DEFAULT_PAGE_SIZE))).thenReturn(new PageImpl<>(pageModel.getBody()));

        mockMvc.perform(get("/members?page={page}",0)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pageModel)))
                .andExpect(jsonPath("body").isArray())
                .andExpect(jsonPath("$.body",hasSize(5)))
                .andExpect(jsonPath("$.body.[0].id",is(1)))
                .andExpect(jsonPath("$.body.[1].id",is(2)))
                .andExpect(jsonPath("$.body.[2].id",is(3)))
                .andExpect(jsonPath("$.body.[3].id",is(4)))
                .andExpect(jsonPath("$.body.[4].id",is(5)))
                .andExpect(jsonPath("$.nextPage",is("This is the last page")))
                .andExpect(jsonPath("$.previuosPage",is("This is the first page")))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = "USER")
    void findAllBadRequest() throws Exception {
        PageModel<MemberEntity> pageModel = new PageModel<>();
        pageModel.setBody(toListEntity());
        pageModel.setNextPage("This is the last page");
        pageModel.setPreviousPage("This is the first page");

        when(memberRepository.findAll(PageRequest.of(0,DEFAULT_PAGE_SIZE))).thenReturn(new PageImpl<>(pageModel.getBody()));

        mockMvc.perform(get("/members?page=")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pageModel)))
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

    private List<MemberEntity> toListEntity() {
        List<MemberEntity> memberEntityList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MemberEntity memberEntity = toEntityTest();
            memberEntity.setId(i + 1l);
            memberEntityList.add(memberEntity);
        }
        return memberEntityList;
    }
}
