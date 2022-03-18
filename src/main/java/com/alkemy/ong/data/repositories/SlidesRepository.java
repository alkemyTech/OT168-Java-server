package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.SlidesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlidesRepository extends JpaRepository<SlidesEntity, Long> {
}
