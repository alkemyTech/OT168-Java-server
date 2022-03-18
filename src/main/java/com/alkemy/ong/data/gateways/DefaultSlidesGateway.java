package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.SlidesEntity;
import com.alkemy.ong.data.repositories.SlidesRepository;
import com.alkemy.ong.domain.slides.Slides;
import com.alkemy.ong.domain.slides.SlidesGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultSlidesGateway implements SlidesGateway {

    private final SlidesRepository slidesRepository;

    public DefaultSlidesGateway(SlidesRepository slidesRepository) {
        this.slidesRepository = slidesRepository;
    }

    @Override
    public List<Slides> findAll() {
        List<SlidesEntity> slidesEntity = this.slidesRepository.findAll();
        return slidesEntity.stream()
                .map(slide -> slidesEntity2Slides(slide))
                .collect(Collectors.toList());
    }

    public static Slides slidesEntity2Slides(SlidesEntity entity){
        return Slides.builder()
                .idSlides(entity.getIdSlides())
                .imageUrl(entity.getImageUrl())
                .text(entity.getText())
                .order(entity.getOrder())
                .deleted(entity.getDeleted())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                //TODO: Agregar Organización
                .build();
    }
}
