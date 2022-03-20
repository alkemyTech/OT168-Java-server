package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.TestimonialEntity;
import com.alkemy.ong.domain.testimonial.Testimonial;
import com.alkemy.ong.domain.testimonial.TestimonialService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    private final TestimonialService testimonialService;

    public TestimonialController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    @PostMapping
    public ResponseEntity<TestimonialDTO> createTestimonial(@RequestBody TestimonialDTO testimonialDTO) throws Exception {
        try {
            if (testimonialDTO.getName() == null || testimonialDTO.getName().isEmpty()) {
                throw new Exception("Field 'name' is required.");
            } else if (testimonialDTO.getContent() == null || testimonialDTO.getContent().isEmpty()) {
                throw new Exception("Field 'content' is required.");
            } else {
                return ResponseEntity.ok(model2DTO(testimonialService.save(dto2Model(testimonialDTO))));
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private Testimonial dto2Model(TestimonialDTO testimonialDTO) {
        Testimonial testimonial = Testimonial.builder()
                .name(testimonialDTO.getName())
                .image(testimonialDTO.getImage())
                .content(testimonialDTO.getContent())
                .createdAt(testimonialDTO.getCreatedAt())
                .updatedAt(testimonialDTO.getUpdatedAt())
                .deleted(testimonialDTO.getDeleted())
                .build();
        return testimonial;
    }

    private TestimonialDTO model2DTO(Testimonial testimonial) {
        TestimonialDTO testimonialDTO = TestimonialDTO.builder()
                .id(testimonial.getId())
                .name(testimonial.getName())
                .image(testimonial.getImage())
                .content(testimonial.getContent())
                .createdAt(testimonial.getCreatedAt())
                .updatedAt(testimonial.getUpdatedAt())
                .deleted(testimonial.getDeleted())
                .build();
        return testimonialDTO;
    }
}
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class TestimonialDTO{
        private Long id;

        private String name;

        private String image;

        private String content;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        private Boolean deleted;
    }
