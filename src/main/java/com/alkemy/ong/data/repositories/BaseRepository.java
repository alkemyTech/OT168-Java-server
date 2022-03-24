package com.alkemy.ong.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository <T,ID> extends JpaRepository<T,ID> {
    Page<T> findAll(Pageable pageable);
}
