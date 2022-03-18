package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.slides.Slides;
import com.alkemy.ong.domain.slides.SlidesService;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/slides")
public class SlidesController {

    private final SlidesService slidesService;

    public SlidesController(SlidesService slidesService) {
        this.slidesService = slidesService;
    }

    @GetMapping
    public List<SlidesResponseDTO> getAllSlides(){
        List<Slides> slidesList =this.slidesService.findAll();
        return slidesList.stream()
                .map(slide -> slides2SlidesResponseDTO(slide))
                .collect(Collectors.toList());
    }

    public static SlidesResponseDTO slides2SlidesResponseDTO(Slides slides){
        return SlidesResponseDTO.builder()
                .imageUrl(slides.getImageUrl())
                .order(slides.getOrder())
                .build();
    }

    @Data
    @Builder
    public static class SlidesResponseDTO{
        private String imageUrl;
        private Integer order;
    }
}
