package com.alkemy.ong.domain.testimonial;

import lombok.*;

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
}
