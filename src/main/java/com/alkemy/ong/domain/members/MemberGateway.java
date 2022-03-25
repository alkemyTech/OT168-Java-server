package com.alkemy.ong.domain.members;

public interface MemberGateway {

    MemberPage findAll(Integer pageNumber);
    Member save(Member member);
    Member findById(Long id);
    void delete(Long id);
    Member update(Member member);
}