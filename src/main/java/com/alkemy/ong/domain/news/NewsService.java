package com.alkemy.ong.domain.news;

import org.springframework.stereotype.Service;

@Service
public class NewsService {

    private final NewsGateway newsGateway;

    public NewsService(NewsGateway newsGateway){
        this.newsGateway = newsGateway;
    }

    public News findById(Long newsId){
        return newsGateway.findById(newsId);
    }

    public News saveNews(News news){
        return newsGateway.saveNews(news);
    }

    public News updateNews(Long newsId, News news){
        return newsGateway.updateNews(newsId, news);
    }

    public void deleteNews(Long newsId){
        newsGateway.deleteNews(newsId);
    }

}