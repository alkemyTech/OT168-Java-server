package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.TestimonialEntity;
import com.alkemy.ong.data.repositories.TestimonialRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.testimonial.Testimonial;
import com.alkemy.ong.domain.testimonial.TestimonialGateway;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class DefaultTestimonialGateway implements TestimonialGateway {

    private final TestimonialRepository testimonialRepository;

    public DefaultTestimonialGateway(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    @SneakyThrows
    public Testimonial save(Testimonial testimonial){
            return toModel(testimonialRepository.save(toEntity(testimonial)));
    }

    @SneakyThrows
    public Testimonial update(Long id, Testimonial testimonial){
        if(id == testimonial.getId()) {
            Optional<TestimonialEntity> entity = testimonialRepository.findById(id);
            entity.get().setName(testimonial.getName());
            entity.get().setContent(testimonial.getContent());
            entity.get().setImage(testimonial.getImage());
            entity.get().setUpdatedAt(LocalDateTime.now());
            return toModel(testimonialRepository.save(entity.get()));
        } else {
            throw new ResourceNotFoundException("The id doesn't match the id of the Testimonial provided.");
        }
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
}
