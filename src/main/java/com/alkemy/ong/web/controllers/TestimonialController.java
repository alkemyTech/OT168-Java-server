package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.testimonial.Testimonial;
import com.alkemy.ong.domain.testimonial.TestimonialService;
import com.alkemy.ong.web.pagination.PageDTO;
import com.alkemy.ong.web.pagination.PageDTOMapper;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import static java.util.stream.Collectors.toList;
import static com.alkemy.ong.web.utils.WebUtils.*;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    private final TestimonialService testimonialService;
    private final PageDTOMapper<TestimonialDTO,Testimonial> pageDTOMapper;

    public TestimonialController(TestimonialService testimonialService, PageDTOMapper pageDTOMapper) {
        this.testimonialService = testimonialService;
        this.pageDTOMapper =pageDTOMapper;
    }

    @PostMapping
    public ResponseEntity<TestimonialDTO> createTestimonial(@Valid @RequestBody TestimonialDTO testimonialDTO) {
        return new ResponseEntity<>(toDto(testimonialService.save(toModel(testimonialDTO))), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestimonialDTO> updateTestimonial(@PathVariable("id") Long id, @Valid @RequestBody TestimonialDTO testimonialDTO) {
        validateDtoIdWithBodyId(id, testimonialDTO.getId());
        return new ResponseEntity<>(toDto(testimonialService.update(id, toModel(testimonialDTO))), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        testimonialService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<PageDTO<TestimonialDTO>> findAll(@RequestParam("page") int numberPage) {
        validatePageNumber(numberPage);
        return ResponseEntity.ok().
                body(pageDTOMapper.toPageDTO(testimonialService.findAll(numberPage), TestimonialDTO.class));
    }

    private Testimonial toModel(TestimonialDTO testimonialDTO) {
        return Testimonial.builder()
                .id(testimonialDTO.getId())
                .name(testimonialDTO.getName())
                .image(testimonialDTO.getImage())
                .content(testimonialDTO.getContent())
                .build();
    }

    private TestimonialDTO toDto(Testimonial testimonial) {
        return TestimonialDTO.builder()
                .id(testimonial.getId())
                .name(testimonial.getName())
                .image(testimonial.getImage())
                .content(testimonial.getContent())
                .build();
    }

    private List<TestimonialDTO> toDTOList(List<Testimonial> testimonialList) {
        return testimonialList.stream().map(this::toDto).collect(toList());
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestimonialDTO {

        @Schema(example = "1", required = true)
        private Long id;

        @Schema(example = "Testimonio", required = true)
        @NotNull(message = "Field 'name' is required.")
        private String name;

        @Schema(example = "testimonio.jpg", required = true)
        private String image;

        @Schema(example = "Compartimos las historias y los testimonios.", required = true)
        @NotNull(message = "Field 'content' is required.")
        private String content;

        @Schema(example = "testimonial", required = true)
        private String type = "testimonial";
    }
}