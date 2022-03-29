package com.alkemy.ong.domain.users;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean deleted;
    private Long role;
}
