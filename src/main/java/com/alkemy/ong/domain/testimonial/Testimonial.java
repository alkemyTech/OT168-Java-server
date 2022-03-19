package com.alkemy.ong.domain.testimonial;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Testimonial {

    private Long id;

    private String name;

    private String image;

    private String content;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Boolean deleted;
}
