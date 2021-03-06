package com.alkemy.ong.domain.comments;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Long id;
    private String body;
    private Long userId;
    private Long newsId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
