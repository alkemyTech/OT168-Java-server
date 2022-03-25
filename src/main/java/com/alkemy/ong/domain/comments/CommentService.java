package com.alkemy.ong.domain.comments;

import java.util.Comparator;
import java.util.List;
import static java.util.stream.Collectors.toList;

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

	public List<Comment> findAll() {
		return commentGateway.findAll().stream().sorted(Comparator.comparing(Comment::getCreatedAt)).collect(toList());
	}

}