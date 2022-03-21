package com.alkemy.ong.domain.activity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    private Long id;
    private String name;
    private String content;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean deleted;
}
