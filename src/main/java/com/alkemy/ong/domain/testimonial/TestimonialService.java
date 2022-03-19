package com.alkemy.ong.domain.testimonial;

import org.springframework.stereotype.Service;

@Service
public class TestimonialService {

    private final TestimonialGateway testimonialGateway;

    public TestimonialService(TestimonialGateway testimonialGateway){
        this.testimonialGateway = testimonialGateway;
    }

}
