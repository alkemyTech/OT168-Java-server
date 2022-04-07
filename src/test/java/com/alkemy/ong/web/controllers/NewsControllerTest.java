package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.pagination.PageModel;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.web.controllers.NewsController.NewsDTO;
import com.alkemy.ong.data.entities.NewsEntity;
import com.alkemy.ong.data.repositories.NewsRepository;

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

import java.util.Arrays;
import java.util.Optional;

import static com.alkemy.ong.data.utils.PaginationUtils.DEFAULT_PAGE_SIZE;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    NewsRepository newsRepository;

    private final String url = "/news";

    @Test
    void findAll() throws Exception {
        PageModel<NewsEntity> pageModelNewsEntity = toPage();

        when(newsRepository.findAll(PageRequest.of(0,DEFAULT_PAGE_SIZE))).thenReturn(new PageImpl<>(pageModelNewsEntity.getBody()));

        mockMvc.perform(get("/news").param("page", "0").contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pageModelNewsEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("body").isArray())
                .andExpect(jsonPath("$.body",hasSize(2)))
                .andExpect(jsonPath("$.body.[0].name",is("Summer Colony")))
                .andExpect(jsonPath("$.body.[1].name",is("Summer Colony")))
                .andExpect(jsonPath("$.nextPage",is("This is the last page")))
                .andExpect(jsonPath("$.previuosPage",is("This is the first page")))
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void findById() throws Exception {
        NewsEntity newsEntity = toModel(1L, "Summer Colony", "Swimming pool for the little ones", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");
        NewsDTO newsDTO = toDTO(1L, "Summer Colony", "Swimming pool for the little ones", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");
        when(newsRepository.findById(1L)).thenReturn(Optional.of(newsEntity));

        mockMvc.perform(post(url + "/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.newsId", is (1)))
                .andExpect(jsonPath("$.name", is ("Summer Colony")))
                .andExpect(jsonPath("$.content", is ("Swimming pool for the little ones")))
                .andExpect(jsonPath("$.image", is ("https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg")));
    }

    @Test
    void getComments() throws Exception {
        NewsEntity newsEntity = toModel(1L, "Summer Colony", "Swimming pool for the little ones", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");
        NewsDTO newsDTO = toDTO(1L, "Summer Colony", "Swimming pool for the little ones", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");
        when(newsRepository.findById(1L)).thenReturn(Optional.of(newsEntity));

        mockMvc.perform(post(url + "/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.newsId", is (1)))
                .andExpect(jsonPath("$.name", is ("Summer Colony")))
                .andExpect(jsonPath("$.content", is ("Swimming pool for the little ones")))
                .andExpect(jsonPath("$.image", is ("https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void saveNews() throws Exception {
        NewsEntity newsEntity = toModel(null, "Summer Colony", "Swimming pool for the little ones", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");
        NewsEntity newsEntity1 = toModel(1L, "Summer Colony", "Swimming pool for the little ones", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg" );
        NewsDTO newsDTO = toDTO(null, "Summer Colony", "Swimming pool for the little ones", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");
        when(newsRepository.save(newsEntity)).thenReturn(newsEntity1);

        mockMvc.perform(post(url)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.newsId", is (1)))
                .andExpect(jsonPath("$.name", is ("Summer Colony")))
                .andExpect(jsonPath("$.content", is ("Swimming pool for the little ones")))
                .andExpect(jsonPath("$.image", is ("https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void saveNewsBadRequest() throws Exception {
        NewsEntity newsEntity = toModel(null, "", "", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");
        NewsEntity newsEntity1 = toModel(1L, "", "", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");
        NewsDTO newsDTO = toDTO(null,"", "", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");
        when(newsRepository.save(newsEntity)).thenReturn(newsEntity1);

        mockMvc.perform(post(url)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateNews() throws Exception{
        NewsEntity newsEntity = toModel(1L, "Summer Colony", "Swimming pool for the little ones", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");
        NewsDTO newsDTO = toDTO(1L, "Summer Colony", "Swimming pool for the little ones", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");
        when(newsRepository.findById(1L)).thenReturn(Optional.of(newsEntity));
        when(newsRepository.save(newsEntity)).thenReturn(newsEntity);

        mockMvc.perform(put(url + "/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.newsId", is (1)))
                .andExpect(jsonPath("$.name", is ("Summer Colony")))
                .andExpect(jsonPath("$.content", is ("Swimming pool for the little ones")))
                .andExpect(jsonPath("$.image", is ("https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateNewsNotFound() throws Exception {
        when(newsRepository.findById(5L)).thenThrow(new ResourceNotFoundException("The ID doesn't exist."));
        NewsDTO newsDTO = toDTO(5L, "Summer Colony", "Swimming pool for the little ones", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");
        mockMvc.perform(put(url + "/5")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isNotFound());
     }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateNewsBadRequest() throws Exception {
        NewsEntity newsEntity = toModel(1L, "", "", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");
        NewsDTO newsDTO = toDTO(1L, "", "", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");
        when(newsRepository.findById(1L)).thenReturn(Optional.of(newsEntity));
        when(newsRepository.save(newsEntity)).thenReturn(newsEntity);

        mockMvc.perform(put(url + "/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateNewsBadId() throws Exception {
        NewsDTO newsDTO = toDTO(1L, "Summer Colony", "Swimming pool for the little ones", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");
        mockMvc.perform(put(url + "/2")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteNewsOk() throws Exception {
        NewsEntity newsEntity = toModel(1L, "", "", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg");

        when(newsRepository.findById(newsEntity.getNewsId())).thenReturn(Optional.of(newsEntity));

        mockMvc.perform(delete("/news/1")).andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteNewsNotFound() throws Exception {
        doThrow(ResourceNotFoundException.class).when(newsRepository).deleteById(789L);

        mockMvc.perform(delete("/news/789")).andExpect(status().isNotFound());
    }

    private NewsEntity toModel(Long newsId, String name, String content, String image){
        return NewsEntity.builder()
                .newsId(newsId)
                .name(name)
                .content(content)
                .image(image)
                .build();
    }

    private NewsDTO toDTO(Long newsId, String name, String content, String image){
        return NewsDTO.builder()
                .newsId(newsId)
                .name(name)
                .content(content)
                .image(image)
                .build();
    }

    private PageModel toPage(){
        return PageModel.builder()
                .body(Arrays.asList(toModel(1l, "Summer Colony", "Swimming pool for the little ones", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg"), toModel(2L, "Summer Colony", "Swimming pool for the little ones", "https://upload.wikimedia.org/wikipedia/commons/9/9f/Olympic_Pool_Munich_1972.jpg")))
                .nextPage("This is the last page")
                .previousPage("This is the first page")
                .build();
    }
}