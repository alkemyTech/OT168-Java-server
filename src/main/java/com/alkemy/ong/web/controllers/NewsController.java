package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.CommentEntity;
import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.domain.news.NewsService;
import com.alkemy.ong.web.pagination.PageDTO;
import com.alkemy.ong.web.pagination.PageDTOMapper;
import com.alkemy.ong.web.utils.WebUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.alkemy.ong.web.utils.WebUtils.validateDtoIdWithBodyId;

@Tag(name = "2. News")
@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;
    private final PageDTOMapper<NewsDTO, News> pageDTOMapper;

    public NewsController(NewsService newsService, PageDTOMapper pageMapper) {
        this.newsService = newsService;
        this.pageDTOMapper = pageMapper;
    }

    @GetMapping
    public ResponseEntity<PageDTO<NewsDTO>> findAll(@RequestParam("page") int numberPage) {
        WebUtils.validatePageNumber(numberPage);
        return ResponseEntity.ok()
                .body(pageDTOMapper
                        .toPageDTO(newsService.findAll(numberPage), NewsDTO.class));
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<NewsDTO> findById(@PathVariable("newsId") Long newsId) {
        News news = newsService.findById(newsId);
        return ResponseEntity.ok(toDTO(news));
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<NewsDTO> getComments(@PathVariable ("id") Long id) {
        News news = newsService.findById(id);
        return ResponseEntity.ok(toDTO(news));
    }

    @PostMapping
    public ResponseEntity<NewsDTO> saveNews(@Valid @RequestBody NewsDTO newsDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(newsService.saveNews(toModel(newsDTO))));
    }

    @PutMapping("/{newsId}")
    public ResponseEntity<NewsDTO> updateNews(@PathVariable Long newsId, @Valid @RequestBody NewsDTO newsDTO) {
        validateDtoIdWithBodyId(newsId, newsDTO.getNewsId());
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
                .comments((newsDTO.getComments()))
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
                .comments(news.getComments())
                .build();
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NewsDTO {

        @Schema(example = "1", required = true)
        private Long newsId;

        @Schema(example = "Guía sobre ciberacoso sexual a niños", required = true)
        @NotEmpty(message = "The name field cannot be empty.")
        private String name;

        @Schema(example = "Para construir una cultura preventiva acerca del delito de acoso a niños a través de medios digitales.", required = true)
        @NotEmpty(message = "The content field cannot be empty.")
        private String content;

        @Schema(example = "https://www.laopinion.com.co/sites/default/files/2022-04/ciberacoso1.jpg", required = true)
        @NotEmpty(message = "The image field cannot be empty.")
        private String image;

        @Schema(pattern = "yyyy-MM-dd HH:mm:ss", example = "2022-04-05 00:15:48", required = true)
        private LocalDateTime createdAt;

        @Schema(pattern = "yyyy-MM-dd HH:mm:ss", example = "2022-04-05 00:15:48", required = true)
        private LocalDateTime updatedAt;

        @Schema(example = "news", required = true)
        private String type = "news";

        @Schema(example = "Comments", required = true)
        private List<CommentEntity> comments = new ArrayList<>();
    }
}