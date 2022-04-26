package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.comments.Comment;
import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.domain.news.NewsService;
import com.alkemy.ong.web.pagination.PageDTO;
import com.alkemy.ong.web.controllers.CommentController.*;
import com.alkemy.ong.web.pagination.PageDTOMapper;
import com.alkemy.ong.web.utils.WebUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import static com.alkemy.ong.web.utils.WebUtils.validateDtoIdWithBodyId;
import static java.util.stream.Collectors.toList;

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

    @Operation(summary = "Show a list of news, using pagination")
    @ApiResponses( value = {@ApiResponse(responseCode = "200", description = "Show a list of news")})
    @GetMapping
    public ResponseEntity<PageDTO<NewsDTO>> findAll(@Parameter(description = "Page number you want to view",example = "0")@RequestParam("page") int numberPage) {
        WebUtils.validatePageNumber(numberPage);
        return ResponseEntity.ok()
                .body(pageDTOMapper
                        .toPageDTO(newsService.findAll(numberPage), NewsDTO.class));
    }

    @Operation(summary = "Find a news by ID")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Show details of the news", content = { @Content( schema = @Schema(implementation = NewsDTO.class))}),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = { @Content( schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "News not found"))}),
    })
    @GetMapping("/{newsId}")
    public ResponseEntity<NewsDTO> findById(@Parameter(example = "1")@PathVariable("newsId") Long newsId) {
        News news = newsService.findById(newsId);
        return ResponseEntity.ok(toDTO(news));
    }

    @Operation(summary = "Show a list of comments by news ID")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Show details of the news", content = { @Content( schema = @Schema(implementation = NewsDTO.class))}),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = { @Content( schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "News not found"))}),
    })
    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<NewsDTO> getComments(@Parameter(example = "1")@PathVariable ("id") Long id) {
        News news = newsService.findById(id);
        return ResponseEntity.ok(toDTO(news));
    }

    @Operation(summary = "Create a news")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "News created successfully",  content = { @Content( schema = @Schema(implementation = NewsDTO.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = { @Content( schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "\"The name field cannot be empty.\" Or \"The content field cannot be empty.\" Or \"The image field cannot be empty.\"")
            )})
    })
    @PostMapping
    public ResponseEntity<NewsDTO> saveNews(@Valid @RequestBody NewsDTO newsDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(newsService.saveNews(toModel(newsDTO))));
    }

    @Operation(summary = "Update a news")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "News updated successfully",  content = { @Content( schema = @Schema(implementation = NewsDTO.class))}),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = { @Content( schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "News not found"))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = { @Content( schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "\"The name field cannot be empty.\" Or \"The content field cannot be empty.\" Or \"The image field cannot be empty.\"")
            )})
    })
    @PutMapping("/{newsId}")
    public ResponseEntity<NewsDTO> updateNews(@Parameter(example = "1")@PathVariable Long newsId, @Valid @RequestBody NewsDTO newsDTO) {
        validateDtoIdWithBodyId(newsId, newsDTO.getNewsId());
        return ResponseEntity.ok(toDTO(newsService.updateNews(newsId, toModel(newsDTO))));
    }

    @Operation(summary = "Delete a news")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "News deleted successfully", content = { @Content( schema = @Schema(implementation = NewsDTO.class))}),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = { @Content( schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "News not found"))}),
    })
    @DeleteMapping("/{newsId}")
    public ResponseEntity<Void> deleteNews(@Parameter(example = "1") @PathVariable Long newsId) {
        newsService.deleteNews(newsId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private News toModel(NewsDTO newsDTO) {
        return News.builder()
                .newsId(newsDTO.getNewsId())
                .name(newsDTO.getName())
                .content(newsDTO.getContent())
                .image(newsDTO.getImage())
                .type(newsDTO.getType())
                .comments(newsDTO.getComments()
                        .stream()
                        .map(this::toCommentModel)
                        .collect(toList()))
                .build();
    }

    private NewsDTO toDTO(News news) {
        news.getComments().stream().map(this::toCommentDTO).collect(toList());
        return NewsDTO.builder()
                .newsId(news.getNewsId())
                .name(news.getName())
                .content(news.getContent())
                .image(news.getImage())
                .type(news.getType())
                .comments(news.getComments()
                        .stream()
                        .map(this::toCommentDTO)
                        .collect(toList()))
                .build();
    }

    private CommentDTO toCommentDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .user(comment.getUserId())
                .newsId(comment.getNewsId())
                .build();
    }

    private Comment toCommentModel(CommentDTO commentDTO) {
        return Comment.builder()
                .body(commentDTO.getBody())
                .userId(commentDTO.getUser())
                .newsId(commentDTO.getNewsId())
                .build();
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "News attributes")
    public static class NewsDTO {

        @Schema(example = "1")
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

        @Schema(example = "news")
        private String type = "news";

        @Schema(example = "Comments")
        private List<CommentDTO> comments = new ArrayList<>();
    }
}