package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.TestimonialEntity;
import com.alkemy.ong.domain.testimonial.Testimonial;
import com.alkemy.ong.domain.testimonial.TestimonialService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    private final TestimonialService testimonialService;

    public TestimonialController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    @PostMapping
    public ResponseEntity createTestimonial(@RequestBody TestimonialDTO testimonialDTO) throws Exception {
        try {
            return new ResponseEntity<>(model2DTO(testimonialService.save(dto2Model(testimonialDTO))), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.fillInStackTrace().getMessage(), HttpStatus.BAD_REQUEST);
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
