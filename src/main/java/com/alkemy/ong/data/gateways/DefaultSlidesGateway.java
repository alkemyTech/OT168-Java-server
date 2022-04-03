package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.SlidesEntity;
import com.alkemy.ong.data.repositories.SlidesRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.slides.Slides;
import com.alkemy.ong.domain.slides.SlidesGateway;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultSlidesGateway implements SlidesGateway {

    private final SlidesRepository slidesRepository;

    public DefaultSlidesGateway(SlidesRepository slidesRepository) {
        this.slidesRepository = slidesRepository;
    }

    @Override
    public List<Slides> findAll() {
        List<SlidesEntity> slidesEntityList = slidesRepository.findAll(Sort.by(Sort.Direction.ASC, "order"));
        return slidesEntityList.stream()
                .map(slides -> toModel(slides))
                .toList();
    }

    @Override
    public Slides findById(Long id) {
        SlidesEntity entity = slidesRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No slide with id: " + id + " exists."));
        return toModel(entity);
    }

    @Override
    public Slides update(Slides slides) {
        SlidesEntity entity = slidesRepository.findById(slides.getIdSlides())
                .orElseThrow(()-> new ResourceNotFoundException("No slide with id: " + slides.getIdSlides() + " exists."));
        return toModel(slidesRepository.save(updateEntity(entity,slides)));
    }

    @Override
    public void delete(Long id) {
        SlidesEntity entity = slidesRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No slide with id: " + id + " exists."));
        entity.setDeleted(Boolean.TRUE);
        slidesRepository.save(entity);
    }

    private Slides toModel(SlidesEntity entity){
        return Slides.builder()
                .idSlides(entity.getIdSlides())
                .imageUrl(entity.getImageUrl())
                .text(entity.getText())
                .order(entity.getOrder())
                .organization(DefaultOrganizationGateway.toModel(entity.getOrganization()))
                .build();
    }
    private SlidesEntity updateEntity(SlidesEntity entity, Slides slides){
        entity.setIdSlides(slides.getIdSlides());
        entity.setImageUrl(slides.getImageUrl());
        entity.setText(slides.getText());
        entity.setOrder(slides.getOrder());
        return entity;
    }
}