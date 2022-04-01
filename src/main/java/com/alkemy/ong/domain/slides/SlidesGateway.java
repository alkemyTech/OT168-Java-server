package com.alkemy.ong.domain.slides;

import java.util.List;

public interface SlidesGateway {
    List<Slides> findAll();
    Slides findById(Long idSlides);
    Slides update(Slides slides);
    void delete(Long id);
}
