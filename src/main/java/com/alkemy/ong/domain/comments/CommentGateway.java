package com.alkemy.ong.domain.comments;

public interface CommentGateway {

    Comment save(Comment comment);
    Comment update(Long id, Comment comment);
    Comment findById(Long id);

}
