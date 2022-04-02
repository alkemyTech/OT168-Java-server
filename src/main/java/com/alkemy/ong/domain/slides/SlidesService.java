package com.alkemy.ong.domain.slides;

import org.springframework.stereotype.Service;

@Service
public class SlidesService {

    private final SlidesGateway slidesGateway;

    public SlidesService(SlidesGateway slidesGateway) {
        this.slidesGateway = slidesGateway;
    }

    public Slides findById(Long id){
        return slidesGateway.findById(id);
    }

    public <List>Slides findAll(){
        return (Slides) slidesGateway.findAll();}

    public Slides updateSlides(Slides slides) { return slidesGateway.update(slides);}

    public void deleteSlideById(Long id){slidesGateway.delete(id);}
}