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


}
