package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.NewsEntity;
import com.alkemy.ong.data.repositories.NewsRepository;
import com.alkemy.ong.domain.news.NewsGateway;
import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import lombok.SneakyThrows;
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

    @Override
        public News saveNews(News news) {
        newsRepository.save(toEntity(news));
        return news;
    }

    private NewsEntity toEntity (News news) {
        NewsEntity newsEntity = NewsEntity.builder()
                .newsId(news.getNewsId())
                .name(news.getName())
                .content(news.getContent())
                .image(news.getImage())
                .createdAt(news.getCreatedAt())
                .updatedAt(news.getUpdatedAt())
                .deleted(news.getDeleted())
                //.categoryId(news.getCategoryId())
                .type(news.getType())
                .build();
        return newsEntity;
    }

    private News toModel (NewsEntity newsEntity) {
        News news = News.builder()
                .newsId(newsEntity.getNewsId())
                .name(newsEntity.getName())
                .content(newsEntity.getContent())
                .image(newsEntity.getImage())
                .createdAt(newsEntity.getCreatedAt())
                .updatedAt(newsEntity.getUpdatedAt())
                .deleted(newsEntity.getDeleted())
                //.categoryId(newsEntity.getCategoryId())
                .type(newsEntity.getType())
                .build();
        return news;
    }
}