package com.alkemy.ong.domain.news;

import com.alkemy.ong.data.pagination.PageModel;

public interface NewsGateway {

    PageModel<News> findAll(int pageNumber);
    News findById(Long newsId);
    News saveNews(News news);
    News updateNews(Long newsId, News news);
    void deleteNews(Long newsId);
}
