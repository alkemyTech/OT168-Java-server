package com.alkemy.ong.domain.members;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberGateway memberGateway;

    public MemberService(MemberGateway memberGateway){
        this.memberGateway=memberGateway;
    }

    public List<Member> findAll(){
        return memberGateway.findAll();
    }

    public Member save(Member member){
        member.setName(member.getName().trim());
        return memberGateway.save(member);
    }

    public void delete(Long id){
        memberGateway.delete(id);
    }

    public Member update(Member member){
        return memberGateway.update(member);
    }
}