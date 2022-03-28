package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.slides.Slides;
import com.alkemy.ong.domain.slides.SlidesService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
    public ResponseEntity<List<SlidesSimpleDTO>> getAllSlides(){
        List<Slides> slidesList =slidesService.findAll();
        return ResponseEntity.ok(slidesList.stream()
                .map(slide -> toDto(slide))
                .collect(toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlidesDto> getSlideById(@PathVariable Long id){
        Slides slides = slidesService.findById(id);
        return ResponseEntity.ok(toFullDto(slides));
    }

    private SlidesSimpleDTO toDto(Slides slides){
        return SlidesSimpleDTO.builder()
                .imageUrl(slides.getImageUrl())
                .order(slides.getOrder())
                .build();
    }

    private SlidesDto toFullDto(Slides slides){
        return SlidesDto.builder()
                .idSlides(slides.getIdSlides())
                .imageUrl(slides.getImageUrl())
                .text(slides.getText())
                .order(slides.getOrder())
                .organization(slides.getOrganization())
                .build();
    }

    @Data
    @Builder
    public static class SlidesSimpleDTO{
        private String imageUrl;
        private Integer order;
    }

    @Data
    @Builder
    public static class SlidesDto{
        private Long idSlides;
        private String imageUrl;
        private String text;
        private Integer order;
        private Organization organization;
    }
}
