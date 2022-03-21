package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.slides.Slides;
import com.alkemy.ong.domain.slides.SlidesService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/slides")
public class SlidesController {

    private final SlidesService slidesService;

    public SlidesController(SlidesService slidesService) {
        this.slidesService = slidesService;
    }

    @GetMapping
    public ResponseEntity<List<SlidesResponseDTO>> getAllSlides(){
        List<Slides> slidesList =slidesService.findAll();
        return ResponseEntity.ok(slidesList.stream()
                .map(slide -> toDto(slide))
                .collect(toList()));
    }

    private SlidesResponseDTO toDto(Slides slides){
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
