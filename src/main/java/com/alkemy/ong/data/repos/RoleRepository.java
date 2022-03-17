package com.alkemy.ong.data.repos;

import com.alkemy.ong.data.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Long, RoleEntity> {
}
