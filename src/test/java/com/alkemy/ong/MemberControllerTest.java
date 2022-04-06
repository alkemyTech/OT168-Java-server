package com.alkemy.ong;

import com.alkemy.ong.data.entities.MemberEntity;
import com.alkemy.ong.data.pagination.PageModel;
import com.alkemy.ong.data.repositories.MemberRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.web.controllers.MemberController.MemberDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
import java.util.Arrays;
import java.util.Optional;

import static com.alkemy.ong.data.utils.PaginationUtils.DEFAULT_PAGE_SIZE;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        MemberEntity entityRequest = buildEntity(null);
        MemberEntity entityResponse = buildEntity(1l);
        MemberDTO memberDTO = buildDto(null);

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

        MemberDTO memberDTO = buildDto(null);

        memberDTO.setName(null);

        mockMvc.perform(post("/members")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteSuccess() throws Exception {

        MemberEntity entity = buildEntity(1l);

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

        ResourceNotFoundException exceptionThrows = assertThrows(ResourceNotFoundException.class,
                () -> {memberRepository.findById(22l);}, "No Member found with ID 22");

        Assertions.assertEquals("No Member found with ID 22", exceptionThrows.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateSuccess() throws Exception {

        MemberEntity entity = buildEntity(1l);
        MemberDTO memberDTO = buildDto(1l);

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
        MemberDTO memberDTO = buildDto(22l);

        when(memberRepository.findById(memberDTO.getId())).thenThrow(new ResourceNotFoundException(memberDTO.getId(),"Member"));


        mockMvc.perform(put("/members/{id}",22)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberDTO)))
                .andExpect(status().isNotFound());

        ResourceNotFoundException exceptionThrows = assertThrows(ResourceNotFoundException.class,
                () -> {memberRepository.findById(memberDTO.getId());}, "No Member found with ID "+memberDTO.getId());

        Assertions.assertEquals("No Member found with ID "+memberDTO.getId(), exceptionThrows.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateBadRequest() throws Exception {

        MemberDTO memberDTO = buildDto(1l);

        memberDTO.setName(null);

        mockMvc.perform(put("/members/{id}",1)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void findAllSuccess() throws Exception {
        PageModel<MemberEntity> pageModel = buildPageModel();

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

        PageModel<MemberEntity> pageModel = buildPageModel();

        mockMvc.perform(get("/members?page=")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pageModel)))
                .andExpect(status().isBadRequest());

    }

    private MemberEntity buildEntity(Long id){
        return MemberEntity.builder()
                .id(id)
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

    private MemberDTO buildDto(Long id){
        return MemberDTO.builder()
                .id(id)
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
    
    private PageModel buildPageModel(){
        return PageModel.builder()
                .body(Arrays.asList(buildEntity(1l), buildEntity(2l), buildEntity(3l),buildEntity(4l),buildEntity(5l)))
                .nextPage("This is the last page")
                .previousPage("This is the first page")
                .build();
    }

}
