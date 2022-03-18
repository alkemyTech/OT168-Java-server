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

}

