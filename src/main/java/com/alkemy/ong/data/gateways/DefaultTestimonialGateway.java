package com.alkemy.ong.data.gateways;

import com.alkemy.ong.domain.testimonial.TestimonialGateway;
import org.springframework.stereotype.Component;

@Component
public class DefaultTestimonialGateway implements TestimonialGateway {

    private final TestimonialGateway testimonialGateway;

    public DefaultTestimonialGateway(TestimonialGateway testimonialGateway) {
        this.testimonialGateway = testimonialGateway;
    }

}
