package com.alkemy.ong.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alkemy.ong.data.entities.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{

}

