package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.members.Member;
import com.alkemy.ong.domain.members.MemberService;
import com.alkemy.ong.web.pagination.PageDTOMapper;
import com.alkemy.ong.web.pagination.PageDTO;
import com.alkemy.ong.web.utils.WebUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@RestController
@RequestMapping
public class MemberController {

    private final MemberService memberService;
    private final PageDTOMapper<MemberDTO,Member> pageDTOMapper;

    public MemberController(MemberService memberService, PageDTOMapper pageDTOMapper) {
        this.memberService = memberService;
        this.pageDTOMapper =pageDTOMapper;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(

                            responseCode = "200",
                            description = "Show list of active members in the system.",
                            content = {
                                    @Content(
                                            schema = @Schema(implementation = PageDTO.class),
                                            examples = @ExampleObject(value = "\"body\" : [\n{\n" +
                                                    "\"id\" : \"1\"\n"+
                                                    "\"name\" : \"Jose Perez\"\n"+
                                                    "\"facebookUrl\" : \"www.josefacebook.com\"\n"+
                                                    "\"instagramUrl\" : \"www.joseinstagram.com\"\n"+
                                                    "\"linkedinUrl\" : \"www.joselinkedin.com\"\n"+
                                                    "\"image\" : \"joseperez.jpg\"\n"+
                                                    "\"description\" : \"Some desciption of Jose Perez\"\n"+
                                                    "\"createdAt\" : \"2022-03-29 18:58:56\"\n"+
                                                    "\"updatedAt\" : \"2022-03-30 19:38:22\"\n"+
                                            "}\n"+
                                            "],\n"+
                                            "\"previuosPage\" : \"This is the first page\"\n"+
                                            "\"nextPage\" : \"This is the last page\"")
                                            )
                            }),
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
    public ResponseEntity<PageDTO<MemberDTO>> findAll(@RequestParam("page") int numberPage) {
        WebUtils.validatePageNumber(numberPage);
        return ResponseEntity.ok()
                .body(pageDTOMapper
                        .toPageDTO(memberService.findAll(numberPage),MemberDTO.class));
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Show registered member information.",
                            content = {
                                    @Content(
                                            schema = @Schema(implementation = PageDTO.class),
                                            examples = @ExampleObject(value = "{\n" +
                                                    "\"id\" : \"1\"\n"+
                                                    "\"name\" : \"Jose Perez\"\n"+
                                                    "\"facebookUrl\" : \"www.josefacebook.com\"\n"+
                                                    "\"instagramUrl\" : \"www.joseinstagram.com\"\n"+
                                                    "\"linkedinUrl\" : \"www.joselinkedin.com\"\n"+
                                                    "\"image\" : \"joseperez.jpg\"\n"+
                                                    "\"description\" : \"Some description of Jose Perez\"\n"+
                                                    "\"createdAt\" : \"2022-03-29 18:58:56\"\n"+
                                                    "\"updatedAt\" : \"2022-03-29 18:58:56\"\n"+
                                                    "}")
                                    )
                            }),
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
    public ResponseEntity<MemberDTO> save(@Valid @RequestBody MemberDTO memberDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toDTO(memberService.save(toModel(memberDTO))));
    }

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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiResponses(
            value = {
                    @ApiResponse(

                            responseCode = "200",
                            description = "Show updated member information.",
                            content = {
                                    @Content(
                                            schema = @Schema(implementation = PageDTO.class),
                                            examples = @ExampleObject(value = "{\n" +
                                                    "\"id\" : \"1\"\n"+
                                                    "\"name\" : \"Jose Perez\"\n"+
                                                    "\"facebookUrl\" : \"www.josefacebook.com\"\n"+
                                                    "\"instagramUrl\" : \"www.joseinstagram.com\"\n"+
                                                    "\"linkedinUrl\" : \"www.joselinkedin.com\"\n"+
                                                    "\"image\" : \"joseperez.jpg\"\n"+
                                                    "\"description\" : \"Some updated description of Jose Perez\"\n"+
                                                    "\"createdAt\" : \"2022-03-29 18:58:56\"\n"+
                                                    "\"updatedAt\" : \"2022-04-01 14:21:48\"\n"+
                                                    "}")
                                    )
                            }),
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
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody MemberDTO member) {
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

        @ApiModelProperty(value = "ID", required = true)
        private Long id;

        @ApiModelProperty(value = "Name")
        @NotBlank(message = "Name field cannot be empty or be null.")
        @Pattern(regexp = "[a-zA-Z ]{0,50}", message = "Name field cannot admit number.")
        private String name;

        @ApiModelProperty(value = "Facebook Url")
        private String facebookUrl;

        @ApiModelProperty(value = "Instagram Url")
        private String instagramUrl;

        @ApiModelProperty(value = "Linkedin Url")
        private String linkedinUrl;

        @ApiModelProperty(value = "Image")
        private String image;

        @ApiModelProperty(value = "Description")
        private String description;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updatedAt;;
    }

}