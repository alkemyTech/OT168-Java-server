package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.slides.Slides;
import com.alkemy.ong.domain.slides.SlidesService;
import com.alkemy.ong.web.utils.WebUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Slides")
@RestController
@RequestMapping("/slides")
public class SlidesController {

    private final SlidesService slidesService;

    public SlidesController(SlidesService slidesService) {
        this.slidesService = slidesService;
    }

    @GetMapping
    public ResponseEntity<List<SlidesSimpleDTO>> getAllSlides(){
        List<Slides> slidesList = slidesService.findAll();
        return ResponseEntity.ok(slidesList
                .stream()
                .map(s -> toDto(s))
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlidesDto> getSlideById(@PathVariable Long id){
        Slides slides = slidesService.findById(id);
        return ResponseEntity.ok(toFullDto(slides));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SlidesDto> update(@PathVariable Long id, @RequestBody SlidesDto slides){
        WebUtils.validateDtoIdWithBodyId(id, slides.getIdSlides());
        SlidesDto slidesDto = toFullDto(slidesService.updateSlides(toModel(slides)));
        return ResponseEntity.ok(slidesDto);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody SlidesDto slides) throws Exception {
        SlidesDto slidesDto = toFullDto(slidesService.createSlides(toModel(slides)));
        return new ResponseEntity<>(slidesDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        slidesService.deleteSlideById(id);
        return ResponseEntity.noContent().build();
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
                .idOrganization(slides.getIdOrganization())
                .build();
    }

    private Slides toModel(SlidesDto slidesDto){
        return Slides.builder()
                .idSlides(slidesDto.getIdSlides())
                .imageUrl(slidesDto.getImageUrl())
                .text(slidesDto.getText())
                .order(slidesDto.getOrder())
                .idOrganization(slidesDto.getIdOrganization())
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
        private Long idOrganization;
    }
}