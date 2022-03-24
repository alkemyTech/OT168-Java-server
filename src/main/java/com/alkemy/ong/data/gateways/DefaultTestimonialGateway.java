package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.TestimonialEntity;
import com.alkemy.ong.data.repositories.TestimonialRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.testimonial.Testimonial;
import com.alkemy.ong.domain.testimonial.TestimonialGateway;
import com.alkemy.ong.domain.testimonial.TestimonialPage;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultTestimonialGateway implements TestimonialGateway {

    private final TestimonialRepository testimonialRepository;

    public DefaultTestimonialGateway(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    @SneakyThrows
    public Testimonial save(Testimonial testimonial) {
        return toModel(testimonialRepository.save(toEntity(testimonial)));
    }

    public Testimonial update(Long id, Testimonial testimonial) {
        TestimonialEntity entity = testimonialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The ID doesn't exist."));
        entity.setName(testimonial.getName());
        entity.setContent(testimonial.getContent());
        entity.setImage(testimonial.getImage());
        entity.setUpdatedAt(LocalDateTime.now());
        return toModel(testimonialRepository.save(entity));
    }

    public void delete(Long id) {
        testimonialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The ID doesn't exist."));
        testimonialRepository.deleteById(id);
    }

    public TestimonialPage findAll(Integer page) {
        Page<TestimonialEntity> entityPage = testimonialRepository.findAll(PageRequest.of(page, 10));
        if (entityPage.isEmpty()) {
            throw new ResourceNotFoundException("The page requested doesn't exists.");
        }
        String previousPage = "";
        String nextPage = "";
        if (entityPage.hasPrevious()) {
            previousPage = String.valueOf(page - 1);
        }
        if (entityPage.hasNext()) {
            nextPage = String.valueOf(page + 1);
        }
        return TestimonialPage.builder().
                testimonialList(toModelList(entityPage.stream().toList())).
                nextPage(nextPage).
                previousPage(previousPage).build();
    }

    private Testimonial toModel(TestimonialEntity testimonialEntity) {
        return Testimonial.builder()
                .id(testimonialEntity.getId())
                .name(testimonialEntity.getName())
                .image(testimonialEntity.getImage())
                .content(testimonialEntity.getContent())
                .createdAt(testimonialEntity.getCreatedAt())
                .updatedAt(testimonialEntity.getUpdatedAt())
                .deleted(testimonialEntity.getDeleted())
                .build();
    }

    private TestimonialEntity toEntity(Testimonial testimonialModel) {
        TestimonialEntity testimonial = TestimonialEntity.builder()
                .id(testimonialModel.getId())
                .name(testimonialModel.getName())
                .image(testimonialModel.getImage())
                .content(testimonialModel.getContent())
                .createdAt(testimonialModel.getCreatedAt())
                .updatedAt(testimonialModel.getUpdatedAt())
                .deleted(testimonialModel.getDeleted())
                .build();
        if (testimonialModel.getDeleted() == null) {
            testimonial.setDeleted(false);
        }
        return testimonial;
    }

    private List<Testimonial> toModelList(List<TestimonialEntity> testimonialEntityList) {
        return testimonialEntityList.stream().map(testimonialEntity -> toModel(testimonialEntity)).collect(Collectors.toList());
    }
}
