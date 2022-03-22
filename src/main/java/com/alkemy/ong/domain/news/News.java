package com.alkemy.ong.domain.news;

import com.alkemy.ong.data.entities.CategoryEntity;
import com.alkemy.ong.domain.category.Category;
import lombok.*;

import java.time.LocalDateTime;

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
    private Boolean deleted;
    private String type;
}