package com.alkemy.ong.domain.members;

import com.alkemy.ong.data.pagination.PageModel;

public interface MemberGateway {

    PageModel<Member> findAll(Integer pageNumber);
    Member save(Member member);
    Member findById(Long id);
    void delete(Long id);
    Member update(Member member);
}