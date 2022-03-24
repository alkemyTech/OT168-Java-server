package com.alkemy.ong.domain.testimonial;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestimonialPage {

    private List<Testimonial> testimonialList;

    private String previousPage;

    private String nextPage;
}
