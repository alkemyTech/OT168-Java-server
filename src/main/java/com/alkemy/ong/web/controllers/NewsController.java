package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.domain.news.NewsService;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(newsService.saveNews(toModel(newsDTO))));
    }

    @PutMapping("/{newsId}")
    public ResponseEntity<NewsDTO> updateNews(@PathVariable Long newsId, @Valid @RequestBody NewsDTO newsDTO) {
        return ResponseEntity.ok(toDTO(newsService.updateNews(newsId, toModel(newsDTO))));
    }

    @DeleteMapping("/{newsId}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long newsId) {
        newsService.deleteNews(newsId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private News toModel(NewsDTO newsDTO) {
        return News.builder()
                .newsId(newsDTO.getNewsId())
                .name(newsDTO.getName())
                .content(newsDTO.getContent())
                .image(newsDTO.getImage())
                .createdAt(newsDTO.getCreatedAt())
                .updatedAt(newsDTO.getUpdatedAt())
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
                .type(news.getType())
                .build();
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class NewsDTO {

        @ApiModelProperty(value = "ID", required = true)
        private Long newsId;
        @ApiModelProperty(value = "Name", required = true)
        @NotEmpty(message = "The name field cannot be empty.")
        private String name;
        @ApiModelProperty(value = "Content", required = true)
        @NotEmpty(message = "The content field cannot be empty.")
        private String content;
        @ApiModelProperty(value = "Image", required = true)
        @NotEmpty(message = "The image field cannot be empty.")
        private String image;
        @ApiModelProperty(value = "Creation Date", required = true)
        private LocalDateTime createdAt;
        @ApiModelProperty(value = "Modification Date", required = true)
        private LocalDateTime updatedAt;
        @ApiModelProperty(value = "Type", required = true)
        private String type = "news";
    }
}