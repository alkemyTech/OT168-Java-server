package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.testimonial.Testimonial;
import com.alkemy.ong.domain.testimonial.TestimonialService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    private final TestimonialService testimonialService;

    public TestimonialController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    @PostMapping
    public ResponseEntity<TestimonialDTO> createTestimonial(@Valid @RequestBody TestimonialDTO testimonialDTO) {
        return new ResponseEntity<>(toDto(testimonialService.save(toModel(testimonialDTO))), HttpStatus.CREATED);

    }

    private Testimonial toModel(TestimonialDTO testimonialDTO) {
        return Testimonial.builder()
                .name(testimonialDTO.getName())
                .image(testimonialDTO.getImage())
                .content(testimonialDTO.getContent())
                .createdAt(testimonialDTO.getCreatedAt())
                .updatedAt(testimonialDTO.getUpdatedAt())
                .deleted(testimonialDTO.getDeleted())
                .build();
    }

    private TestimonialDTO toDto(Testimonial testimonial) {
        return TestimonialDTO.builder()
                .id(testimonial.getId())
                .name(testimonial.getName())
                .image(testimonial.getImage())
                .content(testimonial.getContent())
                .createdAt(testimonial.getCreatedAt())
                .updatedAt(testimonial.getUpdatedAt())
                .deleted(testimonial.getDeleted())
                .build();
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TestimonialDTO{
        private Long id;

        @NotNull(message = "Field 'name' is required.")
        private String name;

        private String image;

        @NotNull(message = "Field 'content' is required.")
        private String content;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        private Boolean deleted;
    }
}
