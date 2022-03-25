package com.alkemy.ong.domain.comments;

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

    public Comment updateComment(Long id, Comment comment){
        return commentGateway.update(id, comment);
    }
}