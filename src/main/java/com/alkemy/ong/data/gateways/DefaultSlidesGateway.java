package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.OrganizationEntity;
import com.alkemy.ong.data.entities.SlidesEntity;
import com.alkemy.ong.data.repositories.SlidesRepository;
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
                .map(DefaultSlidesGateway::toModel)
                .collect(toList());
    }

    public static Slides toModel(SlidesEntity entity){
        return Slides.builder()
                .idSlides(entity.getIdSlides())
                .imageUrl(entity.getImageUrl())
                .text(entity.getText())
                .order(entity.getOrder())
                .deleted(entity.getDeleted())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .organization(DefaultOrganizationGateway.toModel(entity.getOrganization()))
                .build();
    }
}
