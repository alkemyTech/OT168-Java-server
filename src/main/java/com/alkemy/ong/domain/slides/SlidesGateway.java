package com.alkemy.ong.domain.slides;

import java.net.URISyntaxException;
import java.util.List;

public interface SlidesGateway {
    List<Slides> findAll();
    Slides findById(Long idSlides);
    Slides create(Slides slides) throws Exception;
    Slides update(Slides slides);
    void delete(Long id);
}
