package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.NewsEntity;
import com.alkemy.ong.data.repositories.NewsRepository;
import static com.alkemy.ong.web.controllers.NewsController.*;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerTest {

    //Generé equals
    //cambié a public DTO

    //Inyecto el mock
    @Autowired
    private MockMvc mockMvc;

    //Es para mapear el objeto? Cómo se usa?
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    NewsRepository newsRepository;

    //Pruebo la creación ok y la fallida
    @Test
    @WithMockUser(roles = "ADMIN")
    public void saveNewsOk() throws Exception{
        //Construyo el DTO
        NewsDTO newsDTO = toDTO();
        //Construyo dos opciones: con y sin ID
        NewsEntity newsIdFailed = toModel(null);
        NewsEntity newsIdOk = toModel(1L);

        //Cuando lo guardo sin id y cuando retorno con id
        when(newsRepository.save(newsIdFailed)).thenReturn(newsIdOk);
        mockMvc.perform(post("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        //No entiendo para qué sirve
                        .content(objectMapper.writeValueAsString(newsDTO)))
                //Lo que espero que devuelva
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.newsId", is(1)))
                .andExpect(jsonPath("$.name", is("Summer Colony")))
                .andExpect(jsonPath("$.content", is("Sports and pool for the little ones")))
                .andExpect(jsonPath("$.image", is("pool")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void saveNewsFailed() throws Exception{
        NewsDTO newsDTO = toDTO();
        newsDTO.setName(null);
        newsDTO.setContent(null);
        newsDTO.setImage(null);

        mockMvc.perform(post("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getNews() throws Exception{
        List<NewsEntity> newsList = asList(toModel((1L)), toModel(2L), toModel(3L));

        //El findAll sólo se usa en paginación
        when(newsRepository.findAll(ArgumentMatchers.eq(false), ArgumentMatchers.any(Pageable.class))).thenReturn(new PageImpl<>(newsList));
        mockMvc.perform(get("/news?page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("content").isArray());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getNewsById() throws Exception{
        NewsDTO newsDTO = toDTO();
        NewsEntity newsEntity = toModel(1L);

        when(newsRepository.findById(1L)).thenReturn(Optional.of(newsEntity));
        mockMvc.perform(get("/news/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isOk())
                //¿hace falta el casteo?
                .andExpect(jsonPath("$.newsId",is(1)))
                .andExpect(jsonPath("$.name",is("Summer Colony")))
                .andExpect(jsonPath("$.content", is("Sports and pool for the little ones")))
                .andExpect(jsonPath("$.image",is("pool")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateNewsOk() throws Exception{
        NewsDTO newsDTO = toDTO();
        NewsEntity newsEntity = toModel(5L);

        when(newsRepository.findById(5L)).thenReturn(Optional.of(newsEntity));
        when(newsRepository.save(newsEntity)).thenReturn(newsEntity);

        mockMvc.perform(put("/news/5").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.newsId",is(5)))
                .andExpect(jsonPath("$.name",is("Summer Colony")))
                .andExpect(jsonPath("$.content", is("Sports and pool for the little ones")))
                .andExpect(jsonPath("$.image",is("pool")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateNewsFailed() throws Exception{
        NewsDTO newsDTO = NewsDTO.builder().name(null).image(null).build();
        NewsEntity newsEntity = toModel(5L);

        when(newsRepository.findById(5L)).thenReturn(Optional.of(newsEntity));
        when(newsRepository.save(newsEntity)).thenReturn(newsEntity);

        mockMvc.perform(put("/news/{newsId}", 5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateNewsNotFound() throws Exception{
        NewsDTO newsDTO = toDTO();

        when(newsRepository.findById(8L)).thenThrow(new ResourceNotFoundException("News not found"));

        mockMvc.perform(put("/news/{newsId}", 8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteNewsOk() throws Exception{
        NewsEntity newsEntity = toModel(20L);
        when(newsRepository.findById(newsEntity.getNewsId())).thenReturn(Optional.of(newsEntity));

        mockMvc.perform(MockMvcRequestBuilders.delete("/news/{id}", 20))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteNewsFailed() throws Exception{
        when(newsRepository.findById(150L)).thenThrow(new ResourceNotFoundException("News not found"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/news/{newsId}", 150))
                .andExpect(status().isNotFound());
    }

    private NewsEntity toModel (Long newsId){
        return NewsEntity.builder()
                .newsId(newsId)
                .name("Summer Colony")
                .content("Sports and pool for the little ones")
                .image("pool")
                .build();
    }

    private NewsDTO toDTO() {
        return NewsDTO.builder()
                .name("Summer Colony")
                .content("Sports and pool for the little ones")
                .image("pool")
                .build();
    }
}