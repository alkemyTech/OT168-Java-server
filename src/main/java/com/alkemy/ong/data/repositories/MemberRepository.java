package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.MemberEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends BaseRepository<MemberEntity,Long> {
    MemberEntity save(MemberEntity member);
    Optional<MemberEntity> findById(Long aLong);
    void deleteById(Long id);
}
