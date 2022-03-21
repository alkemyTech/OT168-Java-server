package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.TestimonialEntity;
import com.alkemy.ong.data.repositories.TestimonialRepository;
import com.alkemy.ong.domain.testimonial.Testimonial;
import com.alkemy.ong.domain.testimonial.TestimonialGateway;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class DefaultTestimonialGateway implements TestimonialGateway {

    private final TestimonialRepository testimonialRepository;

    public DefaultTestimonialGateway(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    @SneakyThrows
    public Testimonial save(Testimonial testimonial){
        try {
            if (testimonial.getName() == null || testimonial.getName().isEmpty()) {
                throw new Exception("Field 'name' is required.");
            } else if (testimonial.getContent() == null || testimonial.getContent().isEmpty()) {
                throw new Exception("Field 'content' is required.");
            } else {
                TestimonialEntity testimonialEntity = testimonialRepository.save(toEntity(testimonial));
                return toModel(testimonialEntity);
            }
        } catch (Exception ex) {
            throw new Exception("Testimonial could not be saved: " + ex.getMessage());
        }
    }

    private Testimonial toModel(TestimonialEntity testimonialEntity) {
        Testimonial testimonial = Testimonial.builder()
                .id(testimonialEntity.getId())
                .name(testimonialEntity.getName())
                .image(testimonialEntity.getImage())
                .content(testimonialEntity.getContent())
                .createdAt(testimonialEntity.getCreatedAt())
                .updatedAt(testimonialEntity.getUpdatedAt())
                .deleted(testimonialEntity.getDeleted())
                .build();
        return testimonial;
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
