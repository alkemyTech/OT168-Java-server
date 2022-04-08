package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.TestimonialEntity;
import com.alkemy.ong.data.pagination.PageModel;
import com.alkemy.ong.data.repositories.TestimonialRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
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

import java.util.Arrays;
import java.util.Optional;

import static com.alkemy.ong.data.utils.PaginationUtils.DEFAULT_PAGE_SIZE;
import static com.alkemy.ong.web.controllers.TestimonialController.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteTestimonialSuccess() throws Exception{
        TestimonialEntity testimonial = createTestimonialEntity(1L, "Testimonial", "http://amazon3.jpg", "Test content");
        when(testimonialRepository.findById(1L)).thenReturn(Optional.of(testimonial));

        mockMvc.perform(delete(url+"/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteTestimonialNotFound() throws Exception{
        when(testimonialRepository.findById(3L)).thenThrow(new ResourceNotFoundException(3L, "Testimonial"));

        mockMvc.perform(delete(url+"/3"))
                .andExpect(status().isNotFound());

        ResourceNotFoundException exceptionThrows = assertThrows(ResourceNotFoundException.class,
                () -> {testimonialRepository.findById(3L);}, "No Testimonial found with ID 3");

        Assertions.assertEquals("No Testimonial found with ID 3", exceptionThrows.getMessage());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteTestimonialUser() throws Exception{
        mockMvc.perform(delete(url + "/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void findAllTestimonialsSuccess() throws Exception{
        PageModel<TestimonialEntity> pageModel = buildPageModel();

        when(testimonialRepository.findAll(PageRequest.of(0,DEFAULT_PAGE_SIZE))).thenReturn(new PageImpl<>(pageModel.getBody()));

        mockMvc.perform(get(url+"?page={page}",0)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pageModel)))
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
    void findAllTestimonialsBadRequest() throws Exception{
        PageModel<TestimonialEntity> pageModel = buildPageModel();

        mockMvc.perform(get(url+"?page=",0)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pageModel)))
                .andExpect(status().isBadRequest());
    }

    private TestimonialEntity createTestimonialEntity(Long id, String name, String image, String content){
        return TestimonialEntity.builder()
                .id(id)
                .name(name)
                .image(image)
                .content(content)
                .build();
    }

    private TestimonialDTO createTestimonialDTO(Long id, String name, String image, String content){
        return TestimonialDTO.builder()
                .id(id)
                .name(name)
                .image(image)
                .content(content)
                .build();
    }

    private PageModel buildPageModel(){
        return PageModel.builder()
                .body(Arrays.asList(
                        createTestimonialEntity(1L, "Testimonial", "http://amazon3.jpg", "Test content"),
                        createTestimonialEntity(2L, "Testimonial", "http://amazon3.jpg", "Test content"),
                        createTestimonialEntity(3L, "Testimonial", "http://amazon3.jpg", "Test content"),
                        createTestimonialEntity(4L, "Testimonial", "http://amazon3.jpg", "Test content"),
                        createTestimonialEntity(5L, "Testimonial", "http://amazon3.jpg", "Test content")
                ))
                .nextPage("This is the last page")
                .previousPage("This is the first page")
                .build();
    }
}
