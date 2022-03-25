package com.alkemy.ong.domain.members;

import com.alkemy.ong.data.entities.MemberEntity;
import com.alkemy.ong.domain.pagination.GenericModelPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberGateway {

    MemberPage findAll(Integer pageNumber);
    Member save(Member member);
    Member findById(Long id);
    void delete(Long id);
    Member update(Member member);
}