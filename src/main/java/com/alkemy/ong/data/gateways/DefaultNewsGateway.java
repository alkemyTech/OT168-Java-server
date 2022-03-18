package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.NewsEntity;
import com.alkemy.ong.data.repositories.NewsRepository;
import com.alkemy.ong.domain.news.NewsGateway;
import com.alkemy.ong.domain.news.News;
import org.springframework.stereotype.Component;

@Component
public class DefaultNewsGateway implements NewsGateway {

    private final NewsRepository newsRepository;

    public DefaultNewsGateway(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public News findById(Long newsId) {

        NewsEntity newsEntity = newsRepository.findById(newsId).orElse(null);

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
