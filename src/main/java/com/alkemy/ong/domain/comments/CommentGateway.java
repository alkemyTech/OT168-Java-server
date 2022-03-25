package com.alkemy.ong.domain.comments;

import java.util.List;

public interface CommentGateway {

    Comment save(Comment comment);
    
    List<Comment> findAll();
}
