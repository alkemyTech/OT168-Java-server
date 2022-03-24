package com.alkemy.ong.domain.comment;

import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentGateway commentGateway;

    public CommentService(CommentGateway commentGateway) {
        this.commentGateway = commentGateway;
    }

    public Comment saveComment(Comment comment) {
        return commentGateway.save(comment);
    }

    public Comment findById(Long id) {
        return commentGateway.findById(id);
    }
}