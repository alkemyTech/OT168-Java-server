package com.alkemy.ong.web.members;

import com.alkemy.ong.domain.members.Member;
import com.alkemy.ong.domain.members.MemberService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService=memberService;
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberDTO>> findAll(){

        List<MemberDTO> memberDTOList = memberService.findAll()
                .stream()
                .map(memberModel -> toDTO(memberModel))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(memberDTOList);
    }


    private MemberDTO toDTO(Member member) {
        MemberDTO memberDTO = MemberDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .facebookUrl(member.getFacebookUrl())
                .instagramUrl(member.getInstagramUrl())
                .linkedinUrl(member.getLinkedinUrl())
                .image(member.getImage())
                .description(member.getDescription())
                .createdAt(member.getCreatedAt())
                .updateAt(member.getUpdateAt())
                .deleted(member.getDeleted())
                .build();

        return memberDTO;
    }
}

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
class MemberDTO {

    private Long id;
    private String name;
    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    private String image;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private Boolean deleted;
}
