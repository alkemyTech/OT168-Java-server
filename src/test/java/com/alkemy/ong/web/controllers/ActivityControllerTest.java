package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.ActivityEntity;
import com.alkemy.ong.data.repositories.ActivityRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.alkemy.ong.web.controllers.ActivityController.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ActivityRepository activityRepository;

    @Autowired
    ObjectMapper objectMapper;

    private final String url = "/activities";

    @Test
    @WithMockUser(roles = "ADMIN")
    void saveActivity() throws Exception {
        ActivityEntity activity = createActivity(null, "nameExample", "ContentExample", "http://example.com/img.jpg");
        ActivityEntity activitySaved = createActivity(1L, "nameExample", "ContentExample", "http://example.com/img.jpg");
        ActivityDTO activityDTO = createActivityDTO(null, "nameExample", "ContentExample", "http://example.com/img.jpg");
        when(activityRepository.save(activity)).thenReturn(activitySaved);

        mockMvc.perform(post(url)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activityDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is (1)))
                .andExpect(jsonPath("$.name", is ("nameExample")))
                .andExpect(jsonPath("$.content", is ("ContentExample")))
                .andExpect(jsonPath("$.image", is ("http://example.com/img.jpg")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void saveActivityBadRequest() throws Exception {
        ActivityEntity activity = createActivity(null, "", "", "http://example.com/img.jpg");
        ActivityEntity activitySaved = createActivity(1L, "", "", "http://example.com/img.jpg");
        ActivityDTO activityDTO = createActivityDTO(null,"", "", "http://example.com/img.jpg");
        when(activityRepository.save(activity)).thenReturn(activitySaved);

        mockMvc.perform(post(url)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activityDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void saveActivityUser() throws Exception {
        mockMvc.perform(post(url))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateActivity() throws Exception {
        ActivityEntity activityUpdated = createActivity(1L, "nameExampleU", "ContentExample", "http://example.com/img.jpg");
        ActivityDTO activityDTO = createActivityDTO(1L, "nameExampleU", "ContentExample", "http://example.com/img.jpg");
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activityUpdated));
        when(activityRepository.save(activityUpdated)).thenReturn(activityUpdated);

        mockMvc.perform(put(url + "/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activityDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is (1)))
                .andExpect(jsonPath("$.name", is ("nameExampleU")))
                .andExpect(jsonPath("$.content", is ("ContentExample")))
                .andExpect(jsonPath("$.image", is ("http://example.com/img.jpg")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateActivityNotFound() throws Exception {
        when(activityRepository.findById(5L)).thenThrow(new ResourceNotFoundException("The ID doesn't exist."));
        ActivityDTO activityDTO = createActivityDTO(5L, "nameExampleU", "ContentExample", "http://example.com/img.jpg");
        mockMvc.perform(put(url + "/5")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activityDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateActivityBadRequest() throws Exception {
        ActivityEntity activityUpdated = createActivity(1L, "", "", "http://example.com/img.jpg");
        ActivityDTO activityDTO = createActivityDTO(1L, "", "", "http://example.com/img.jpg");
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activityUpdated));
        when(activityRepository.save(activityUpdated)).thenReturn(activityUpdated);

        mockMvc.perform(put(url + "/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activityDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateActivityBadID() throws Exception {
        ActivityDTO activityDTO = createActivityDTO(1L, "nameExampleU", "ContentExample", "http://example.com/img.jpg");
        mockMvc.perform(put(url + "/2")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activityDTO)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(roles = "USER")
    void updateActivityUser() throws Exception {
        mockMvc.perform(put(url + "/1"))
                .andExpect(status().isForbidden());
    }

    private ActivityEntity createActivity(Long id, String name, String content, String image){
        return ActivityEntity.builder()
                .id(id)
                .name(name)
                .content(content)
                .image(image)
                .build();
    }

    private ActivityDTO createActivityDTO(Long id, String name, String content, String image){
        return ActivityDTO.builder()
                .id(id)
                .name(name)
                .content(content)
                .image(image)
                .build();
    }
}