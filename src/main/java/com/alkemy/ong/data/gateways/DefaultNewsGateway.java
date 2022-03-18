package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.NewsEntity;
import com.alkemy.ong.data.repositories.NewsRepository;
import com.alkemy.ong.domain.news.NewsGateway;
import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class DefaultNewsGateway implements NewsGateway {

    private final NewsRepository newsRepository;

    public DefaultNewsGateway(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @SneakyThrows
    @Override
    public News findById(Long newsId) {
        NewsEntity newsEntity = newsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("The ID doesn't exist."));
        return toModel(newsEntity);
    }

    private News toModel (NewsEntity newsEntity) {
        News news = News.builder()
                .newsId(newsEntity.getNewsId())
                .name(newsEntity.getName())
                .content(newsEntity.getContent())
                .image(newsEntity.getImage())
                //.category(newsEntity.getCategory())
                .createdAt(newsEntity.getCreatedAt())
                .updatedAt(newsEntity.getUpdatedAt())
                .deleted(newsEntity.getDeleted())
                .build();
        return news;
    }

}
