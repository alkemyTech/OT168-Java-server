package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.TestimonialEntity;
import com.alkemy.ong.data.pagination.PageModel;
import com.alkemy.ong.data.pagination.PageModelMapper;
import com.alkemy.ong.data.repositories.TestimonialRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.testimonial.Testimonial;
import com.alkemy.ong.domain.testimonial.TestimonialGateway;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import static com.alkemy.ong.data.utils.PaginationUtils.*;

@Component
public class DefaultTestimonialGateway implements TestimonialGateway {

    private final TestimonialRepository testimonialRepository;
    private final PageModelMapper<Testimonial, TestimonialEntity> pageModelMapper;

    public DefaultTestimonialGateway(TestimonialRepository testimonialRepository, PageModelMapper<Testimonial, TestimonialEntity> pageModelMapper) {
        this.testimonialRepository = testimonialRepository;
        this.pageModelMapper = pageModelMapper;
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
       //entity.setUpdatedAt(LocalDateTime.now());
        return toModel(testimonialRepository.save(entity));
    }

    public void delete(Long id) {
        testimonialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The ID doesn't exist."));
        testimonialRepository.deleteById(id);
    }

    public PageModel<Testimonial> findAll(int pageNumber) {
        return pageModelMapper.toPageModel(setPagesNumbers(testimonialRepository
                .findAll(PageRequest.of(pageNumber, DEFAULT_PAGE_SIZE)),"/testimonials?page="), Testimonial.class);
    }

    //TODO: Borrar lineas comentadas si el rework funciona!
    private static Testimonial toModel(TestimonialEntity testimonialEntity) {
        return Testimonial.builder()
                .id(testimonialEntity.getId())
                .name(testimonialEntity.getName())
                .image(testimonialEntity.getImage())
                .content(testimonialEntity.getContent())
                //.createdAt(testimonialEntity.getCreatedAt())
                //.updatedAt(testimonialEntity.getUpdatedAt())
                .build();
    }

    //TODO: Borrar lineas comentadas si el rework funciona!
    private TestimonialEntity toEntity(Testimonial testimonialModel) {
        return TestimonialEntity.builder()
                .id(testimonialModel.getId())
                .name(testimonialModel.getName())
                .image(testimonialModel.getImage())
                .content(testimonialModel.getContent())
                //.createdAt(testimonialModel.getCreatedAt())
                //.updatedAt(testimonialModel.getUpdatedAt())
                .build();
    }
}
