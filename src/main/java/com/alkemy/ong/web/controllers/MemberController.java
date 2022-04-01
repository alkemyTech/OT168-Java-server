package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.members.Member;
import com.alkemy.ong.domain.members.MemberService;
import com.alkemy.ong.web.pagination.PageDTOMapper;
import com.alkemy.ong.web.pagination.PageDTO;
import com.alkemy.ong.web.utils.WebUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;


@Tag(name = "members")
@RestController
@RequestMapping
public class MemberController {

    private final MemberService memberService;
    private final PageDTOMapper<MemberDTO,Member> pageDTOMapper;

    public MemberController(MemberService memberService, PageDTOMapper pageDTOMapper) {
        this.memberService = memberService;
        this.pageDTOMapper =pageDTOMapper;
    }

    @Operation(description = "Show a list of active members in the system, using pagination", operationId = "findAll", summary = "Show a list of the members actives")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Show list of active members in the system."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "BAD REQUEST",
                            content = {
                                    @Content(
                                            schema = @Schema(implementation = String.class),
                                            examples = @ExampleObject(
                                                    name = "Message of error",
                                                    summary = "400 from the server directly.",
                                                    value = "The page does not exist"
                                            )
                                    )
                            }
                    )
            })
    @GetMapping("/members")
    public ResponseEntity<PageDTO<MemberDTO>> findAll(@Parameter(description = "Page number you want to view",example = "0")@RequestParam("page") int numberPage) {
        WebUtils.validatePageNumber(numberPage);
        return ResponseEntity.ok()
                .body(pageDTOMapper
                        .toPageDTO(memberService.findAll(numberPage),MemberDTO.class));
    }

    @Operation(description = "Adds an member to the system", operationId = "save", summary = "Adds an active member to the system")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Show registered member information."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "BAD REQUEST",
                            content = { @Content(
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(
                                            name = "Message of error",
                                            summary = "400 from the server directly.",
                                            value = "\"Name field cannot admit number.\" or \"Name field cannot be empty or be null.\"")
                            )
                            }
                    )
            })
    @PostMapping("/members")
    public ResponseEntity<MemberDTO> save(@Parameter(description = "Member to add") @Valid @RequestBody MemberDTO memberDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toDTO(memberService.save(toModel(memberDTO))));
    }


    @Operation(description = "Remove a member from the system", operationId = "delete", summary = "Change member status to inactive")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Member successfully deleted."),
                    @ApiResponse(
                            responseCode = "404",
                            description = "NOT FOUND",
                            content = {
                                    @Content(
                                            schema = @Schema(implementation = String.class),
                                            examples = @ExampleObject(
                                                    name = "Message of error",
                                                    summary = "404 from the server directly.",
                                                    value = "The ID doesn't exist."
                                            )
                                    )
                            }
                    )
            })
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID of the member to delete",example = "1")@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(description = "Information of the member to update", operationId = "update", summary = "Update member information")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Show updated member information."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "BAD REQUEST",
                            content = {
                                    @Content(schema = @Schema(implementation = String.class),
                                            examples = @ExampleObject(
                                                    name = "Message of error",
                                                    summary = "400 from the server directly.",
                                                    value = "\"PathId does not match with RequestBody ID.\" or \"Name field cannot be empty or be null.\" or \"Name field cannot admit number.\"" )
                                            )
                                    }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "NOT FOUND",
                            content = { @Content(
                                            schema = @Schema(implementation = String.class),
                                            examples = @ExampleObject(
                                                    name = "Message of error",
                                                    summary = "404 from the server directly.",
                                                    value = "The ID doesn't exist.")
                                            )
                                    }
                    )
            })
    @PutMapping("members/{id}")
    public ResponseEntity<MemberDTO> update(@Parameter(description = "ID of the member to update",example = "1")@PathVariable Long id,
                                            @Parameter(description = "Member to add") @Valid @RequestBody MemberDTO member) {
        WebUtils.validateDtoIdWithBodyId(id, member.getId());
        return ResponseEntity.ok(toDTO(memberService.update(toModel(member))));
    }

    private MemberDTO toDTO(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .facebookUrl(member.getFacebookUrl())
                .instagramUrl(member.getInstagramUrl())
                .linkedinUrl(member.getLinkedinUrl())
                .image(member.getImage())
                .description(member.getDescription())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    private Member toModel(MemberDTO memberDTO) {
        return Member.builder()
                .id(memberDTO.getId())
                .name(memberDTO.getName())
                .facebookUrl(memberDTO.getFacebookUrl())
                .instagramUrl(memberDTO.getInstagramUrl())
                .linkedinUrl(memberDTO.getLinkedinUrl())
                .image(memberDTO.getImage())
                .description(memberDTO.getDescription())
                .createdAt(memberDTO.getCreatedAt())
                .updatedAt(memberDTO.getUpdatedAt())
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    @Valid
    private static class MemberDTO {

        @Schema(example = "1", required = true)
        private Long id;

        @Schema(example = "Jose Perez", required = true)
        @NotBlank(message = "Name field cannot be empty or be null.")
        @Pattern(regexp = "[a-zA-Z ]{0,50}", message = "Name field cannot admit number.")
        private String name;

        @Schema(example = "wwww.facebook.com", required = true)
        private String facebookUrl;

        @Schema(example = "wwww.instagram.com", required = true)
        private String instagramUrl;

        @Schema(example = "wwww.linkedin.com", required = true)
        private String linkedinUrl;

        @Schema(example = "photo.jpg", required = true)
        private String image;

        @Schema(example = "some description of the member", required = true)
        private String description;

        @Schema(pattern = "yyyy-MM-dd HH:mm:ss", example = "2022-03-29 18:58:56", required = true)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;

        @JsonProperty("updatedAt")
        @Schema(pattern = "yyyy-MM-dd HH:mm:ss", example = "2022-03-29 18:58:56", required = true)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updatedAt;;
    }

}