package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.domain.news.NewsService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

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
        if (news != null) {
            buildDTO(news);
        }
        return ResponseEntity.ok(news);

    }

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