package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.domain.news.NewsService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    //la respuesta tiene q ser un ResponseEntity.ok para q devuelva 200 y el body el newsDto
    @GetMapping("/{newsId}")
    public ResponseEntity<News> findById(@PathVariable("newsId") Long newsId) {
        News news = newsService.findById(newsId);
        if (news != null) {
            return ResponseEntity.ok(news)
                    .body(buildDTO(news));
        }else
            return (ResponseEntity<News>) ResponseEntity
                    .status(HttpStatus.NOT_FOUND);
        }


    //Podemos mejorarlo creando un metodo privado tipo buildDto(model) q retorne el dto.
    private NewsDTO buildDTO(News news) {
        NewsDTO newsDTO = NewsDTO.builder()
                .newsId(news.getNewsId())
                .name(news.getName())
                .content(news.getContent())
                .image(news.getImage())
                //.category(news.getCategory())
                .createdAt(news.getCreatedAt())
                .updatedAt(news.getUpdatedAt())
                .deleted(news.getDeleted())
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

    private String name;

    private String content;

    private String image;

    //private Category category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean deleted;

}