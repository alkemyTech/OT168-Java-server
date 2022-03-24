package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    Page<MemberEntity> findAll(Pageable pageable);
    MemberEntity save(MemberEntity member);
    Optional<MemberEntity> findById(Long aLong);
    void deleteById(Long id);
}
