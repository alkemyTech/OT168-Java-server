package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.comments.Comment;
import com.alkemy.ong.domain.comments.CommentService;
import com.alkemy.ong.web.utils.WebUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import static java.util.stream.Collectors.toList;

import static com.alkemy.ong.web.utils.WebUtils.*;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    @GetMapping
    public ResponseEntity<List<CommentSlimDTO>> getAllComments(){
    	List<CommentSlimDTO> commentsSlimDTO;
    	commentsSlimDTO = commentService.findAll().stream().map(this::toSlimDTO).collect(toList());
    	return ResponseEntity.ok(commentsSlimDTO);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> saveComment(@Valid @RequestBody CommentDTO commentDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(commentService.saveComment(toModel(commentDTO))));
    }
    
    private CommentSlimDTO toSlimDTO(Comment comment) {
    	CommentSlimDTO newCommentSlimDTO = CommentSlimDTO.builder().body(comment.getBody()).build();
    	return newCommentSlimDTO;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @Valid @RequestBody CommentDTO commentDTO){
        validateDtoIdWithBodyId(id, commentDTO.getId());
        return ResponseEntity.ok(toDTO(commentService.updateComment(id, toModel(commentDTO))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private Comment toModel(CommentDTO commentDTO){
        return Comment.builder()
                .body(commentDTO.getBody())
                .userId(commentDTO.getUserId())
                .newsId(commentDTO.getNewsId())
                .createdAt(commentDTO.getCreatedAt())
                .updatedAt(commentDTO.getUpdatedAt())
                .build();
    }

    private CommentDTO toDTO(Comment comment){
        return CommentDTO.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .userId(comment.getUserId())
                .newsId(comment.getNewsId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class CommentDTO {
        private Long id;
        @NotEmpty(message = "Body can't be empty")
        private String body;
        @NotNull(message = "User can't be null")
        private Long userId;
        @NotNull(message = "News can't be null")
        private Long newsId;
        @JsonFormat(pattern="dd-MM-yyyy hh:mm")
        private LocalDateTime createdAt;
        @JsonFormat(pattern="dd-MM-yyyy hh:mm")
        private LocalDateTime updatedAt;
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