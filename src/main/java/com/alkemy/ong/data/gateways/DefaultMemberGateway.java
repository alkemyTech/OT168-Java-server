package com.alkemy.ong.data.gateways;

import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.members.Member;
import com.alkemy.ong.domain.members.MemberGateway;

import com.alkemy.ong.data.entities.MemberEntity;
import com.alkemy.ong.data.repositories.MemberRepository;
import com.alkemy.ong.domain.members.MemberPage;
import com.alkemy.ong.domain.pagination.GenericModelPage;
import com.alkemy.ong.domain.pagination.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultMemberGateway implements MemberGateway {

    private final MemberRepository memberRepository;

    public DefaultMemberGateway(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberPage findAll(Integer pageNumber) {
        GenericModelPage<MemberEntity,Member> genericModelPage = generatePagination(memberRepository.findAll(PageRequest.of(pageNumber, 10)));
        Page<MemberEntity> entities = memberRepository.findAll(PageRequest.of(pageNumber, 10));
        Page<Member> models;

        MemberPage memberPage =  MemberPage.builder()
                .memberList(genericModelPage.getListModel())
                .nextPage(genericModelPage.getNextPage())
                .previuosPage(genericModelPage.getPreviousPage())
                .build();

        return memberPage;
    }

    @Override
    public Member save(Member member) {
        return toModel(memberRepository.save(toEntity(member)));
    }

    @Override
    public Member findById(Long id) {
        return toModel(memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The ID doesn't exist.")));
    }

    @Override
    public void delete(Long id) {
        findById(id);
        memberRepository.deleteById(id);
    }

    @Override
    public Member update(Member member) {
        MemberEntity memberEntity = toEntity(findById(member.getId()));
        return toModel(memberRepository.save(toUpdate(memberEntity, member)));
    }

    private Member toModel(MemberEntity memberEntity) {
        return Member.builder()
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
    }

    private MemberEntity toEntity(Member member) {
        return MemberEntity.builder()
                .id(member.getId())
                .name(member.getName().trim())
                .facebookUrl(member.getFacebookUrl())
                .instagramUrl(member.getInstagramUrl())
                .linkedinUrl(member.getLinkedinUrl())
                .image(member.getImage())
                .description(member.getDescription())
                .createdAt(member.getCreatedAt())
                .build();
    }

    private MemberEntity toUpdate(MemberEntity memberEntity, Member member) {
        memberEntity.setName(member.getName().trim());
        memberEntity.setFacebookUrl(member.getFacebookUrl());
        memberEntity.setInstagramUrl(member.getInstagramUrl());
        memberEntity.setLinkedinUrl(member.getLinkedinUrl());
        memberEntity.setImage(member.getImage());
        memberEntity.setDescription(member.getDescription());
        return memberEntity;
    }

    private GenericModelPage<MemberEntity, Member> generatePagination(Page<MemberEntity> pageEntity){
        GenericModelPage<MemberEntity,Member> genericModelPage = new GenericModelPage<>();
        genericModelPage.setModelPage(pageEntity);
        genericModelPage.setListModel(pageEntity
                .getContent()
                .stream()
                .map(this::toModel)
                .collect(toList()));
        Pagination.findAll(pageEntity.getNumber(), genericModelPage,"/members?page=");
        return genericModelPage;
    }
}