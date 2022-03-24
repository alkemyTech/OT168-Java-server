package com.alkemy.ong.domain.news;

public interface NewsGateway {

    News findById(Long newsId);
    News saveNews(News news);
    News updateNews(Long newsId, News news);
    void deleteNews(Long newsId);
}
