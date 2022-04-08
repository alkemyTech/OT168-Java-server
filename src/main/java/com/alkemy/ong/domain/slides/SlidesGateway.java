package com.alkemy.ong.domain.slides;

import java.io.IOException;
import java.util.List;

public interface SlidesGateway {
    List<Slides> findAll();
    Slides findById(Long idSlides);
    Slides create(Slides slides) throws IOException;
    Slides update(Slides slides);
    void delete(Long id);
}
