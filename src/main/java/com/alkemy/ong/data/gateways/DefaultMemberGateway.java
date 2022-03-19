package com.alkemy.ong.data.gateways;

import com.alkemy.ong.domain.members.Member;
import com.alkemy.ong.domain.members.MemberGateway;

import com.alkemy.ong.data.entities.MemberEntity;
import com.alkemy.ong.data.repositories.MemberRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultMemberGateway implements MemberGateway {

    private final MemberRepository memberRepository;

    public DefaultMemberGateway(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Member> findAll() {

        List<Member> memberList = memberRepository.findAll().stream()
                .map(memberEntity -> toModel(memberEntity))
                .collect(Collectors.toList());

        return memberList;
    }

    @Override
    public Member save(Member member) {

        MemberEntity memberEntity = memberRepository.save(toEntity(member));
        return toModel(memberEntity);

    }

    private Member toModel(MemberEntity memberEntity) {
        Member memberModel = Member.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .facebookUrl(memberEntity.getFacebookUrl())
                .instagramUrl(memberEntity.getInstagramUrl())
                .linkedinUrl(memberEntity.getLinkedinUrl())
                .image(memberEntity.getImage())
                .description(memberEntity.getDescription())
                .createdAt(memberEntity.getCreatedAt())
                .updatedAt(memberEntity.getUpdatedAt())
                .deleted(memberEntity.getDeleted())
                .build();
        return memberModel;
    }
}
/*
List<ContactDTO> dto;

           dto = contactService.getContacts().stream()
                    .map(c -> toDto(c))
                    .collect(Collectors.toList());*/