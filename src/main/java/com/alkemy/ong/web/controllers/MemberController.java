package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.pagination.PageMapper;
import com.alkemy.ong.domain.members.Member;
import com.alkemy.ong.domain.members.MemberService;
import com.alkemy.ong.web.utils.PageDTO;
import com.alkemy.ong.web.utils.WebUtils;
import lombok.*;
import org.springframework.http.HttpStatus;
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
    private final PageMapper<MemberDTO,Member> pageMapper;

    public MemberController(MemberService memberService, PageMapper bodyMapper) {
        this.memberService = memberService;
        this.pageMapper =bodyMapper;
    }

    @GetMapping("/members")
    public ResponseEntity<PageDTO<MemberDTO>> findAll(@RequestParam("page") int numberPage) {
        WebUtils.validatePageNumber(numberPage);
        return ResponseEntity.ok()
                .body(pageMapper
                        .toPageDTO(memberService.findAll(numberPage),MemberDTO.class));
    }

    @PostMapping("/members")
    public ResponseEntity<MemberDTO> save(@Valid @RequestBody MemberDTO memberDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toDTO(memberService.save(toModel(memberDTO))));
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

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

        private Long id;

        @NotBlank(message = "Name field cannot be empty or be null.")
        @Pattern(regexp = "[a-zA-Z ]{0,50}", message = "Name field cannot admit number.")
        private String name;
        private String facebookUrl;
        private String instagramUrl;
        private String linkedinUrl;
        private String image;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

}