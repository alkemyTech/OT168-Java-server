package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.CategoryEntity;
import com.alkemy.ong.data.entities.NewsEntity;
import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.domain.news.NewsService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<News> findById(@PathVariable("newsId") Long newsId) {
        News news = newsService.findById(newsId);
        buildDTO(news);
        return ResponseEntity.ok(news);
    }

    //POST /news - Deberá validar la existencia de los campos enviados,
    //para almacenar el registro en la tabla News. Antes de almacenarla,
    // deberá asignarle la columna type con el valor "news".

    @PostMapping()
        public ResponseEntity<NewsDTO> saveNews(@Validated @RequestBody NewsEntity newsEntity){
        News news = newsService.saveNews(newsEntity);
        return (ResponseEntity<NewsDTO>) ResponseEntity.status(HttpStatus.CREATED);
        }

    private NewsDTO buildDTO(News news) {
        NewsDTO newsDTO = NewsDTO.builder()
                .newsId(news.getNewsId())
                .name(news.getName())
                .content(news.getContent())
                .image(news.getImage())
                .createdAt(news.getCreatedAt())
                .updatedAt(news.getUpdatedAt())
                .deleted(news.getDeleted())
                .categoryId(news.getCategoryId())
                .type(news.getType())
                .build();
        return newsDTO;
    }

}

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class NewsDTO {

    private Long newsId;
    @NotEmpty(message = "The name field cannot be empty.")
    private String name;
    @NotEmpty(message = "The content field cannot be empty.")
    private String content;
    @NotEmpty(message = "The image field cannot be empty.")
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean deleted;
    private CategoryEntity categoryId;
    private String type = "news";
}