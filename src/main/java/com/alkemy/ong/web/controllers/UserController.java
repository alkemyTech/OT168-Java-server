package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.exceptions.ForbiddenException;
import com.alkemy.ong.domain.security.jwt.JwtUtil;
import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserService;
import com.alkemy.ong.web.utils.WebUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok()
                .body(userService.findAll()
                .stream()
                .map(this::toDTO)
                .collect(toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id){
        WebUtils.validateDtoIdWithBodyId(id,userDTO.getId());
        return ResponseEntity.ok().body(toDTO(userService.update(toModel(userDTO))));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        verifyUser(id, token);
        return ResponseEntity.ok(toDTO(userService.findById(id)));
    }

    private void verifyUser(Long id, String token){
        if (userService.findByEmail(jwtUtil.extractEmail(token)).getId() != id)
            throw new ForbiddenException("Does not have authorization");
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

    private User toModel(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .photo(userDTO.getPhoto())
                .createdAt(userDTO.getCreatedAt())
                .updatedAt(userDTO.getUpdatedAt())
                .roleId(userDTO.getRoleId())
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
