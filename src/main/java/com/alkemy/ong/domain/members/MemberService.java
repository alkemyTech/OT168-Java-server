package com.alkemy.ong.domain.members;

import com.alkemy.ong.data.pagination.ModelPage;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberGateway memberGateway;

    public MemberService(MemberGateway memberGateway){
        this.memberGateway=memberGateway;
    }

    public ModelPage<Member> findAll(Integer pageNumber){
        return memberGateway.findAll(pageNumber);
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