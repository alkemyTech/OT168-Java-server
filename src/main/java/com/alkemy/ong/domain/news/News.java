package com.alkemy.ong.domain.news;

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

    //private Category category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean deleted;
}