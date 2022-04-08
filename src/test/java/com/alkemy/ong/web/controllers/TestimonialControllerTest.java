package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.TestimonialEntity;
import com.alkemy.ong.data.repositories.TestimonialRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.testimonial.Testimonial;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;
import java.util.Optional;

import static com.alkemy.ong.web.controllers.TestimonialController.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class TestimonialControllerTest {

    @Autowired
     MockMvc mockMvc;
    @Autowired
     ObjectMapper objectMapper;

    @MockBean
    TestimonialRepository testimonialRepository;

    private final String url = "/testimonials";


    @Test
    @WithMockUser(roles = "ADMIN")
    void createTestimonial() throws Exception{
    TestimonialEntity testimonial = createTestimonialEntity(null, "Testimonial", "http://amazon3.jpg", "Test content");
    TestimonialEntity testimonialSaved = createTestimonialEntity(1L, "Testimonial", "http://amazon3.jpg", "Test content");
    TestimonialDTO testimonialDTO = createTestimonialDTO(null, "Testimonial", "http://amazon3.jpg", "Test content");
    when(testimonialRepository.save(testimonial)).thenReturn(testimonialSaved);

    mockMvc.perform(post(url)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testimonialDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is("Testimonial")))
            .andExpect(jsonPath("$.image", is("http://amazon3.jpg")))
            .andExpect(jsonPath("$.content", is("Test content")));
            //.andExpect(jsonPath("$.createdAt",is("2022-03-29T18:58:56")))
            //.andExpect(jsonPath("$.updatedAt",is("2022-03-29T18:58:56")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void createTestimonialBadRequest() throws Exception{
        TestimonialEntity testimonial = createTestimonialEntity(null, "", "http://amazon3.jpg", "Test content");
        TestimonialEntity testimonialSaved = createTestimonialEntity(1L, "", "http://amazon3.jpg", "Test content");
        TestimonialDTO testimonialDTO = createTestimonialDTO(null, "", "http://amazon3.jpg", "Test content");
        when(testimonialRepository.save(testimonial)).thenReturn(testimonialSaved);

        mockMvc.perform(post(url)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testimonialDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void createTestimonialUser() throws Exception {
        mockMvc.perform(post(url))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateTestimonial() throws Exception{
        TestimonialEntity testimonialUpdated = createTestimonialEntity(1L, "Testimonial", "http://amazon3.jpg", "Test content");
        TestimonialDTO testimonialDTO = createTestimonialDTO(1L, "Testimonial", "http://amazon3.jpg", "Test content");
        when(testimonialRepository.findById(1L)).thenReturn(Optional.of(testimonialUpdated));
        when(testimonialRepository.save(testimonialUpdated)).thenReturn(testimonialUpdated);

        mockMvc.perform(put(url + "/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testimonialDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Testimonial")))
                .andExpect(jsonPath("$.image", is("http://amazon3.jpg")))
                .andExpect(jsonPath("$.content", is("Test content")));
                //.andExpect(jsonPath("$.createdAt",is("2022-03-29T18:58:56")))
                //.andExpect(jsonPath("$.updatedAt",is("2022-03-29T18:58:56")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateTestimonialNotFount() throws Exception{
        when(testimonialRepository.findById(3L)).thenThrow(new ResourceNotFoundException(3L, "Testimonial"));
        TestimonialDTO testimonialDTO = createTestimonialDTO(3L, "Testimonial", "http://amazon3.jpg", "Test content");

        mockMvc.perform(put(url + "/3")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testimonialDTO)))
                .andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateTestimonialBadRequest() throws Exception{
        TestimonialEntity testimonialUpdated = createTestimonialEntity(1L, "Testimonial", "http://amazon3.jpg", null);
        TestimonialDTO testimonialDTO = createTestimonialDTO(1L, "Testimonial", "http://amazon3.jpg", null);
        when(testimonialRepository.findById(1L)).thenReturn(Optional.of(testimonialUpdated));
        when(testimonialRepository.save(testimonialUpdated)).thenReturn(testimonialUpdated);

        mockMvc.perform(put(url + "/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testimonialDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateTestimonialBadId() throws Exception{
        TestimonialDTO testimonialDTO = createTestimonialDTO(1L, "Testimonial", "http://amazon3.jpg", "Test content");

        mockMvc.perform(put(url + "/5")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(testimonialDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateTestimonialUser() throws Exception{
        mockMvc.perform(put(url + "/1"))
                .andExpect(status().isForbidden());
    }


    /**
     * DTO y Entities Borrar despues solo separación visual
     */


    //TODO: Borrar lineas comentadas si el rework funciona!
    private TestimonialEntity createTestimonialEntity(Long id, String name, String image, String content){
        return TestimonialEntity.builder()
                .id(id)
                .name(name)
                .image(image)
                .content(content)
                //.createdAt(LocalDateTime.of(2022,03,29,18,58,56))
                //.updatedAt(LocalDateTime.of(2022,03,29,18,58,56))
                .build();
    }

    //TODO: Borrar lineas comentadas si el rework funciona!
    private Testimonial createTestimonial(Long id, String name, String image, String content){
        return Testimonial.builder()
                .id(id)
                .name(name)
                .image(image)
                .content(content)
                //.createdAt(LocalDateTime.of(2022,03,29,18,58,56))
                //.updatedAt(LocalDateTime.of(2022,03,29,18,58,56))
                .build();
    }
    //TODO: Borrar lineas comentadas si el rework funciona!
    private TestimonialDTO createTestimonialDTO(Long id, String name, String image, String content){
        return TestimonialDTO.builder()
                .id(id)
                .name(name)
                .image(image)
                .content(content)
                //.createdAt(LocalDateTime.of(2022,03,29,18,58,56))
                //.updatedAt(LocalDateTime.of(2022,03,29,18,58,56))
                .build();
    }
}
