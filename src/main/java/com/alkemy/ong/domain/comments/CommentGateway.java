package com.alkemy.ong.domain.comments;

import com.alkemy.ong.data.entities.CommentEntity;

import java.util.List;

public interface CommentGateway {

    List<CommentEntity> getAllCommentsByPost();

    Comment save(Comment comment);

    Comment update(Long id, Comment comment);

    Comment findById(Long id);

    List<Comment> findAll();

    void delete(Long id);
}
