package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.exceptions.ForbiddenException;
import com.alkemy.ong.domain.security.jwt.JwtUtil;
import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserService;
import com.alkemy.ong.web.pagination.PageDTO;
import com.alkemy.ong.web.pagination.PageDTOMapper;
import com.alkemy.ong.web.utils.WebUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Tag(name = "4. Users")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PageDTOMapper<UserDTO,User> pageDTOMapper;

    public UserController(UserService userService, JwtUtil jwtUtil,PageDTOMapper<UserDTO,User> pageDTOMapper) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.pageDTOMapper=pageDTOMapper;
    }

    @Operation(summary = "Delete a user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully", content = { @Content( schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = { @Content( schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "User not found"))}),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@Parameter(example = "1")@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Update a user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = { @Content( schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = { @Content( schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "User not found"))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = { @Content( schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "\"The email field is required.\" Or \"The password field is required.\" Or \"This field must be an email\"")
            )})
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@Parameter(example = "1")@Valid @RequestBody UserDTO userDTO, @PathVariable Long id){
        WebUtils.validateDtoIdWithBodyId(id,userDTO.getId());
        return ResponseEntity.ok().body(toDTO(userService.update(toModel(userDTO))));
    }

    @Operation(summary = "Find a user by ID")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Show details of the user", content = { @Content( schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = { @Content( schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "User not found"))}),
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@Parameter(example = "1")@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        verifyUser(id, token);
        return ResponseEntity.ok(toDTO(userService.findById(id)));
    }
    @Operation(description = "Show a list of active users in the system, using pagination", operationId = "findAll", summary = "Show a list of the users actives")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Show list of active users in the system."),
                    @ApiResponse(
                            responseCode = "400", description = "BAD REQUEST",
                            content = { @Content(schema = @Schema(implementation = String.class),
                                            examples = @ExampleObject(
                                                    name = "Message of error",
                                                    summary = "400 from the server directly.",
                                                    value = "The page does not exist"
                                            )
                                    )
                            }
                    )
            })
    @GetMapping(params = "page")
    public ResponseEntity<PageDTO<UserDTO>> findAll(@Parameter(description = "Page number you want to view",example = "0")@RequestParam("page") int pageNumber) {
        WebUtils.validatePageNumber(pageNumber);
        return ResponseEntity.ok()
                .body(pageDTOMapper.toPageDTO(userService.findAll(pageNumber),UserDTO.class));
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
    @Schema(description = "User attributes")
    public static class UserDTO {
        @Schema(example = "1")
        private Long id;
        @Schema(required = true, example = "Juan")
        private String firstName;
        @Schema(required = true, example = "Perez")
        private String lastName;
        @Schema(required = true, example = "admin@gmail.com")
        private String email;
        @Schema(required = true, example = "12345678")
        private String password;
        @Schema(example = "http://photoExample.com")
        private String photo;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updatedAt;
        @Schema(example = "USER")
        private Long roleId;
    }
}
