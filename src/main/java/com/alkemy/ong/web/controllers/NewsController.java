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

import javax.validation.Valid;
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
    public ResponseEntity<NewsDTO> findById(@PathVariable("newsId") Long newsId) {
        News news = newsService.findById(newsId);
        return ResponseEntity.ok(toDTO(news));
    }

    @PostMapping
    public ResponseEntity<NewsDTO> saveNews(@Valid @RequestBody NewsDTO newsDTO) {
        newsService.saveNews(toModel(newsDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(newsDTO);
    }

    private News toModel(NewsDTO newsDTO) {
        return News.builder()
                .newsId(newsDTO.getNewsId())
                .name(newsDTO.getName())
                .content(newsDTO.getContent())
                .image(newsDTO.getImage())
                .createdAt(newsDTO.getCreatedAt())
                .updatedAt(newsDTO.getUpdatedAt())
                .deleted(newsDTO.getDeleted())
                .type(newsDTO.getType())
                .build();
    }

    private NewsDTO toDTO(News news) {
        return NewsDTO.builder()
                .newsId(news.getNewsId())
                .name(news.getName())
                .content(news.getContent())
                .image(news.getImage())
                .createdAt(news.getCreatedAt())
                .updatedAt(news.getUpdatedAt())
                .deleted(news.getDeleted())
                .type(news.getType())
                .build();
    }


    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private class NewsDTO {

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
        private String type = "news";
    }
}