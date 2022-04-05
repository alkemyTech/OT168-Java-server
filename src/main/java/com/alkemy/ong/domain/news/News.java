package com.alkemy.ong.domain.news;

import com.alkemy.ong.data.entities.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class News {

    private Long newsId;
    private String name;
    private String content;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String type;
    private List<CommentEntity> comments = new ArrayList<>();
}