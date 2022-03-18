package com.alkemy.ong.domain.contact;

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
    private Boolean deleted;
}