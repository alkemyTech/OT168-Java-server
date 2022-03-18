package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
}
