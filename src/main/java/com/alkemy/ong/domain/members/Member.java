package com.alkemy.ong.domain.members;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Member {

    private Long id;
    private String name;
    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    private String image;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
