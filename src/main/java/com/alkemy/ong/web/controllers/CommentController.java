package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.comments.Comment;
import com.alkemy.ong.domain.comments.CommentService;
import com.alkemy.ong.domain.news.NewsService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

import static com.alkemy.ong.web.utils.WebUtils.*;

@Tag(name = "Comments")
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final NewsService newsService;

    public CommentController(CommentService commentService, NewsService newsService) {
        this.commentService = commentService;
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<List<CommentSlimDTO>> getAllComments() {
        List<CommentSlimDTO> commentsSlimDTO;
        commentsSlimDTO = commentService.findAll().stream().map(this::toSlimDTO).collect(toList());
        return ResponseEntity.ok(commentsSlimDTO);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> saveComment(@Valid @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(commentService.saveComment(toModel(commentDTO))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @Valid @RequestBody CommentDTO commentDTO) {
        validateDtoIdWithBodyId(id, commentDTO.getId());
        return ResponseEntity.ok(toDTO(commentService.updateComment(id, toModel(commentDTO))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private Comment toModel(CommentDTO commentDTO) {
        return Comment.builder()
                .body(commentDTO.getBody())
                .userId(commentDTO.getUser())
                .newsId(commentDTO.getNewsId())
                .build();
    }

    private CommentDTO toDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .user(comment.getUserId())
                .newsId(comment.getNewsId())
                .build();
    }

    private CommentSlimDTO toSlimDTO(Comment comment) {
        return CommentSlimDTO.builder().body(comment.getBody()).build();
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentDTO {

        @Schema(example = "1", required = true)
        private Long id;

        @Schema(example = "Felicitaciones", required = true)
        @NotEmpty(message = "Body can't be empty")
        private String body;

        @Schema(example = "4", required = true)
        @NotNull(message = "User can't be null")
        private Long user;

        @Schema(example = "1", required = true)
        @NotNull(message = "News can't be null")
        private Long newsId;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class CommentSlimDTO {
        private String body;
    }
}