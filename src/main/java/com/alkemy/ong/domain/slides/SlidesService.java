package com.alkemy.ong.domain.slides;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlidesService {

    private final SlidesGateway slidesGateway;

    public SlidesService(SlidesGateway slidesGateway) {
        this.slidesGateway = slidesGateway;
    }

    public List<Slides> findAll(){
        return slidesGateway.findAll();
    }
}
