package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    List<MemberEntity> findAll();
    MemberEntity save(MemberEntity member);
    Optional<MemberEntity> findById(Long aLong);
}
