package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.OrganizationEntity;
import com.alkemy.ong.data.entities.SlidesEntity;
import com.alkemy.ong.domain.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {
}
