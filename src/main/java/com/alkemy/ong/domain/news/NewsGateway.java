package com.alkemy.ong.domain.news;

import com.alkemy.ong.data.entities.NewsEntity;

public interface NewsGateway {

    News findById(Long newsId);
    News saveNews(NewsEntity newsEntity);
}
