package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.CommentEntity;
import com.alkemy.ong.data.entities.NewsEntity;
import com.alkemy.ong.data.entities.UserEntity;
import com.alkemy.ong.data.repositories.CommentRepository;
import com.alkemy.ong.data.repositories.NewsRepository;
import com.alkemy.ong.data.repositories.UserRepository;
import com.alkemy.ong.domain.comments.Comment;
import com.alkemy.ong.domain.comments.CommentGateway;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;

import java.util.List;
import static java.util.stream.Collectors.toList;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class DefaultCommentGateway implements CommentGateway {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;

    public DefaultCommentGateway(CommentRepository commentRepository, UserRepository userRepository, NewsRepository newsRepository){
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return toModel(commentRepository.save(toEntity(comment)));
    }
    
    @Override
	public List<Comment> findAll() {
    	List<CommentEntity> comments = commentsByDescOrder();
    	return comments.stream().map(this::toModel).collect(toList());
	}
    
    private List<CommentEntity> commentsByDescOrder(){
    	return commentRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    private UserEntity getUserEntity(Long id){
      return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "user"));
    }

    private NewsEntity getNewsEntity(Long newsId){
        return newsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException(newsId, "news"));
    }

    @Override
    public Comment update(Long id, Comment comment) {
        CommentEntity updateComment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "comment"));
        updateComment.setBody(comment.getBody());
        updateComment.setUserEntity(getUserEntity(comment.getUserId()));
        updateComment.setNewsEntity(getNewsEntity(comment.getNewsId()));
        return toModel(commentRepository.save(updateComment));
    }

    public Comment findById(Long id){
        return toModel(commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "comment")));
    }

    public void delete(Long id) {
        CommentEntity comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "comment"));
        commentRepository.deleteById(comment.getId());
    }


    private CommentEntity toEntity (Comment comment){
        return CommentEntity.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .userEntity(getUserEntity(comment.getUserId()))
                .newsEntity(getNewsEntity(comment.getNewsId()))
                .build();
    }

    private Comment toModel (CommentEntity commentEntity){
        return Comment.builder()
                .id(commentEntity.getId())
                .body(commentEntity.getBody())
                .userId(commentEntity.getUserEntity().getId())
                .newsId(commentEntity.getNewsEntity().getNewsId())
                .createdAt(commentEntity.getCreatedAt())
                .updatedAt(commentEntity.getUpdatedAt())
                .build();
    }
}
