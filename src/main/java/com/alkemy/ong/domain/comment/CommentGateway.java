package com.alkemy.ong.domain.comment;


public interface CommentGateway {

    Comment save(Comment comment);
    Comment findById(Long id);
}
