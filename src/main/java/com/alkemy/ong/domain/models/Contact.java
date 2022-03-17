package com.alkemy.ong.domain.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}