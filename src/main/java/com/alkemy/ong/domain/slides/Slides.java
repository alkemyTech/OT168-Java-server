package com.alkemy.ong.domain.slides;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Slides {

    private Long idSlides;
    private String imageUrl;
    private String text;
    private Integer order;
    private Boolean deleted = Boolean.FALSE;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    //private Organization organization;
}
