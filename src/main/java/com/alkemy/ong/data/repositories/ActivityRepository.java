package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {
}
