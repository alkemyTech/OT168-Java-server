package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.CommentEntity;
import com.alkemy.ong.domain.comments.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
