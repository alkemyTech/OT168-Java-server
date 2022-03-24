package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.CommentEntity;
import com.alkemy.ong.data.entities.NewsEntity;
import com.alkemy.ong.data.entities.UserEntity;
import com.alkemy.ong.data.repositories.CommentRepository;
import com.alkemy.ong.data.repositories.NewsRepository;
import com.alkemy.ong.data.repositories.UserRepository;
import com.alkemy.ong.domain.comment.Comment;
import com.alkemy.ong.domain.comment.CommentGateway;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
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

    public Comment findById(Long id){
        return toModel(commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The ID doesn't exist.")));
    }

    private UserEntity getUserEntity(Long id){
      return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The User ID doesn't exist."));
    }

    private NewsEntity getNewsEntity(Long newsId){
        return newsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("The news ID doesn't exist."));
    }

    private CommentEntity toEntity (Comment comment){
        return CommentEntity.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .userEntity(getUserEntity(comment.getUserId()))
                .newsEntity(getNewsEntity(comment.getNewsId()))
                .deleted(false)
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
