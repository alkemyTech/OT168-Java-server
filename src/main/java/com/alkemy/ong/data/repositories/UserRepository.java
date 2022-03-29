package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.UserEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    List<UserEntity> findAll();
	
	Optional<UserEntity> findByEmail(String email);
}
