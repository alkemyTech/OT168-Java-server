package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.SlidesEntity;
import com.alkemy.ong.data.repositories.SlidesRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.slides.Slides;
import com.alkemy.ong.domain.slides.SlidesGateway;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultSlidesGateway implements SlidesGateway {

    private final SlidesRepository slidesRepository;

    public DefaultSlidesGateway(SlidesRepository slidesRepository) {
        this.slidesRepository = slidesRepository;
    }

    @Override
    public List<Slides> findAll() {
        List<SlidesEntity> slidesEntity = slidesRepository.findAll();
        return slidesEntity.stream()
                .map(s -> toModel(s))
                .collect(toList());
    }

    @Override
    public Slides findById(Long idSlides) {
        SlidesEntity entity = slidesRepository.findById(idSlides)
                .orElseThrow(()-> new ResourceNotFoundException("No slide with id: " + idSlides + " exists."));
        return toModel(entity);
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
}
