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
        return toModel(newsRepository.save(toEntity(news)));
    }

    @Override
    public News updateNews(Long newsId, News news){
        NewsEntity newsEntity = newsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("The ID doesn't exist."));
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
                .createdAt(news.getCreatedAt())
                .updatedAt(news.getUpdatedAt())
                .type(news.getType())
                .build();
    }

    private News toModel(NewsEntity newsEntity) {
        return News.builder()
                .newsId(newsEntity.getNewsId())
                .name(newsEntity.getName())
                .content(newsEntity.getContent())
                .image(newsEntity.getImage())
                .createdAt(newsEntity.getCreatedAt())
                .updatedAt(newsEntity.getUpdatedAt())
                .type(newsEntity.getType())
                .build();
    }
}