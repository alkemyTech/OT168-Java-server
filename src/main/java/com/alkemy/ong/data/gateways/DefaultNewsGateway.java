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
    public News saveNews(NewsEntity newsEntity) {
        newsRepository.save(newsEntity);
        return toModel(newsEntity);
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
                //.type(newsEntity.getType())
                .build();
        return news;
    }
}
