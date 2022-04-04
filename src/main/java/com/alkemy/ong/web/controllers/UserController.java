package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.exceptions.ForbiddenException;
import com.alkemy.ong.domain.security.jwt.JwtUtil;
import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwt;

    public UserController(UserService userService, JwtUtil jwt) {
        this.userService = userService;
        this.jwt = jwt;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok()
                .body(userService.findAll()
                .stream()
                .map(this::toDTO)
                .collect(toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        if (!(userService.findByEmail(jwt.extractUsername(token.replace("Bearer ", ""))).getId() == id)) {
            throw new ForbiddenException("Does not have authorization");
        }
        return ResponseEntity.ok(toDTO(userService.findById(id)));
    }

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .photo(user.getPhoto())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .roleId(user.getRoleId())
                .build();
    }



    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    private static class UserDTO {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String photo;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updatedAt;
        private Long roleId;
    }
}
