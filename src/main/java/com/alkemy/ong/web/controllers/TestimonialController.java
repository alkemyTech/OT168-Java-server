package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.testimonial.TestimonialService;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TestimonialController {

    private final TestimonialService testimonialService;

    public TestimonialController(TestimonialService testimonialService){
        this.testimonialService = testimonialService;
    }

    @RequestMapping("/testimonials")
    @PostMapping
    public void createTestimonial(@RequestBody TestimonialDTO testimonialDTO) throws Exception{
        try {
            if (testimonialDTO.name == null || testimonialDTO.name.isEmpty()) {
                throw new Exception("Field 'name' is required.");
            } else if (testimonialDTO.content == null || testimonialDTO.content.isEmpty()) {
                throw new Exception("Field 'content' is required.");
            } else {
                // TODO: develop Save method in 'TestimonialService'.
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * TestimonialDTO
     */
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class TestimonialDTO{
        private Long id;

        private String name;

        private String image;

        private String content;

        private LocalDateTime created;

        private LocalDateTime updated;

        private Boolean deleted;
    }

}
