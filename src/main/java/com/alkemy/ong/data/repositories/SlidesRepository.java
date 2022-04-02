package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.SlidesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlidesRepository extends JpaRepository<SlidesEntity, Long> {
    List<SlidesEntity> findByOrder(Long idOrganization);
}
