package com.alkemy.ong.web.controllers;

import static com.alkemy.ong.web.controllers.NewsController.*;
import com.alkemy.ong.data.entities.NewsEntity;
import com.alkemy.ong.data.repositories.NewsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerTest {

    //Generé equals
    //cambié a public DTO

    //Inyecto el mock
    @Autowired
    private MockMvc mockMvc;

    //Es para mapear el objeto?
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    NewsRepository newsRepository;

    //Pruebo la creación ok y la fallida
    @Test
    @WithMockUser(roles = "ADMIN")
    public void saveNewsOk() throws Exception{
        //Construyo el DTO
        NewsDTO newsDTO = ;
        //Construyo dos opciones: con y sin ID
        NewsEntity newsIdFailed = toModel(null);
        NewsEntity newsIdOk = toModel();

        //Cuando lo guardo sin id y cuando retorno con id
        when(newsRepository.save(newsIdFailed)).thenReturn(newsIdOk);
        mockMvc.perform(post("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                //Lo que espero que devuelva
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.newsId", is(1)))
                .andExpect((ResultMatcher)jsonPath("$.name", is("Summer Colony")))
                .andExpect((ResultMatcher)jsonPath("$.content", is("Sports and pool for the little ones")))
                .andExpect((ResultMatcher)jsonPath("$.image", is("pool")));
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

/*    @Test
    @WithMockUser(roles = "USER")
    public void getNews() throws Exception{
        List<NewsEntity> newsEntityList = asList(toModel(1L, "Summer Colony", "Sports and pool for the little ones", "pool"),
        toModel(2L, "Summer Colony", "Sports and pool for the little ones", "pool"),
                toModel(3L, "Summer Colony", "Sports and pool for the little ones", "pool"));

        //El findAll sólo se usa en paginación
        when(newsRepository.findAll(ArgumentMatchers.eq(false), ArgumentMatchers.any(PageDTO.class))).thenReturn(new PageDTOMapper<>(newsEntityList));
        mockMvc.perform(get("/news?page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("content").isArray());
    }*/

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
                .andExpect((ResultMatcher) jsonPath("$.newsId",is(1L)))
                .andExpect((ResultMatcher) jsonPath("$.name",is("Summer Colony")))
                .andExpect((ResultMatcher) jsonPath("$.content", is("Sports and pool for the little ones")))
                .andExpect((ResultMatcher) jsonPath("$.image",is("pool")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateNewsOk() throws Exception{
        NewsDTO newsDTO = toDTO();
        NewsEntity newsEntity = toModel(7L);

        when(newsRepository.findById(7L)).thenReturn(Optional.of(newsEntity));
        when(newsRepository.save(newsEntity)).thenReturn(newsEntity);

        mockMvc.perform(put("/news/5").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.newsId",is(7L)))
                .andExpect((ResultMatcher) jsonPath("$.name",is("Summer Colony")))
                .andExpect((ResultMatcher) jsonPath("$.content", is("Sports and pool for the little ones")))
                .andExpect((ResultMatcher) jsonPath("$.image",is("pool")));
    }

    @Test
    @WithMockUser(roles = "ADMIN"
    public void updateNewsFailed() throws Exception{
        NewsDTO newsDTO = NewsDTO.builder().name(null).content(null).image(null).build();
        NewsEntity newsEntity = toModel(3L);

        when(newsRepository.findById(3L)).thenReturn(Optional.of(newsEntity));
        when(newsRepository.save(newsEntity)).thenReturn(newsEntity);

        mockMvc.perform(put("/news/{newsId}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateNewsNotFound() throws Exception{
        NewsDTO newsDTO = toDTO();

        when(newsRepository.findById(6L)).thenThrow(new ResourceNotFoundException("Non-existent news"));

        mockMvc.perform(put("/news/{newsId}", 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteNewsOk() throws Exception{
        NewsEntity newsEntity = toModel(80L);
        when(newsRepository.findById(newsEntity.getNewsId())).thenReturn(Optional.of(newsEntity));

        mockMvc.perform(MockMvcRequestBuilders.delete("/news/{id}", 80))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteNewsFailed() throws Exception{
        when(newsRepository.findById(90L)).thenThrow(new ResourceNotFoundException("Non-existent news"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/news/{newsId}", 90))
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