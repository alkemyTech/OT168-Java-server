package com.alkemy.ong.domain.members;

;

import java.util.List;

public interface MemberGateway {

    List<Member> findAll();
    Member save(Member member);
    Member findById(Long id);
    void delete(Long id);
}
