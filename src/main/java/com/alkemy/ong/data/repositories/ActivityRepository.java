package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
