package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.CommentEntity;
import com.alkemy.ong.data.entities.NewsEntity;
import com.alkemy.ong.data.pagination.PageModel;
import com.alkemy.ong.data.pagination.PageModelMapper;
import com.alkemy.ong.data.repositories.CommentRepository;
import com.alkemy.ong.data.repositories.NewsRepository;
import com.alkemy.ong.data.repositories.UserRepository;
import com.alkemy.ong.data.utils.PaginationUtils;
import com.alkemy.ong.domain.comments.Comment;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.domain.news.NewsGateway;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.alkemy.ong.data.utils.PaginationUtils.DEFAULT_PAGE_SIZE;
import static java.util.stream.Collectors.toList;

@Component
public class DefaultNewsGateway implements NewsGateway {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final PageModelMapper<News, NewsEntity> pageModelMapper;

    public DefaultNewsGateway(NewsRepository newsRepository, PageModelMapper pageModelMapper,UserRepository userRepository) {
        this.newsRepository = newsRepository;
        this.pageModelMapper = pageModelMapper;
        this.userRepository =userRepository;
    }

    @Override
    public PageModel<News> findAll(int pageNumber){
        return pageModelMapper.toPageModel(PaginationUtils
                .setPagesNumbers(newsRepository
                        .findAll(PageRequest
                        .of(pageNumber, DEFAULT_PAGE_SIZE)), "/news?page="), News.class);
    }

    @SneakyThrows
    @Override
    public News findById(Long newsId) {
        NewsEntity newsEntity = newsRepository.findById(newsId).
                orElseThrow(() -> new ResourceNotFoundException("The ID doesn't exist."));
        return toModel(newsEntity);
    }

    @Override
    public News saveNews(News news) {
        return toModel(newsRepository.save(toEntity(news)));
    }

    @Override
    public News updateNews(Long newsId, News news){
        NewsEntity newsEntity = newsRepository.findById(newsId).
                orElseThrow(() -> new ResourceNotFoundException("The ID doesn't exist."));
        newsEntity.setName(newsEntity.getName());
        newsEntity.setContent(newsEntity.getContent());
        newsEntity.setImage(newsEntity.getImage());
        newsEntity.setType(newsEntity.getType());
        return toModel(newsRepository.save(toEntity(news)));
    }

    @Override
    public void deleteNews(Long newsId) {
        newsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("The ID doesn't exist."));
        newsRepository.deleteById(newsId);
    }

    private NewsEntity toEntity(News news) {
        return NewsEntity.builder()
                .newsId(news.getNewsId())
                .name(news.getName())
                .content(news.getContent())
                .image(news.getImage())
                .type(news.getType())
                .comments(news.getComments()
                        .stream()
                        .map(this::toCommentEntity)
                        .collect(toList()))
                .build();
    }

    private News toModel(NewsEntity newsEntity) {
        return News.builder()
                .newsId(newsEntity.getNewsId())
                .name(newsEntity.getName())
                .content(newsEntity.getContent())
                .image(newsEntity.getImage())
                .type(newsEntity.getType())
                .comments(newsEntity.getComments()
                        .stream()
                        .map(this::toCommentModel)
                        .collect(toList()))
                .build();
    }

    private Comment toCommentModel(CommentEntity commentEntity) {
        return Comment.builder()
                .id(commentEntity.getId())
                .body(commentEntity.getBody())
                .userId(commentEntity.getUserEntity().getId())
                .newsId(commentEntity.getNewsEntity().getNewsId())
                .build();
    }

    private CommentEntity toCommentEntity(Comment comment) {
        return CommentEntity.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .userEntity(userRepository.findById(comment.getUserId()).orElseThrow(()->new ResourceNotFoundException(comment.getUserId(),"User")))
                .newsEntity(newsRepository.findById(comment.getNewsId()).orElseThrow(()->new ResourceNotFoundException(comment.getNewsId(),"News")))
                .build();
    }

}