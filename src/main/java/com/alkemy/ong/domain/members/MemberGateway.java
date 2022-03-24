package com.alkemy.ong.domain.members;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberGateway {

    List<Member> findAll(Pageable pageable);
    Member save(Member member);
    Member findById(Long id);
    void delete(Long id);
    Member update(Member member);
}