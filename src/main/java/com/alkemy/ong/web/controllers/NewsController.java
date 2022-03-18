package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.domain.news.NewsService;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

     /*    @PostMapping
    public News saveNews(News news) {
        if (news != null) {
            //El type se asigna desde Category
        }
        //return
    }*/

    @GetMapping("/{newsId}")
    public NewsDTO findById(@PathVariable("newsId") Long newsId) {
        News news = newsService.findById(newsId);

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