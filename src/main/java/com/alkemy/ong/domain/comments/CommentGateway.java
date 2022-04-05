package com.alkemy.ong.domain.comments;

import java.util.List;

public interface CommentGateway {

    Comment save(Comment comment);

    Comment update(Long id, Comment comment);

    Comment findById(Long id);

    List<Comment> findAll();

    void delete(Long id);
}