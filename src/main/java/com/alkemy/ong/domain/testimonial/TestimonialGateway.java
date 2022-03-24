package com.alkemy.ong.domain.testimonial;

public interface TestimonialGateway {

    Testimonial save(Testimonial testimonial);

    Testimonial update(Long id, Testimonial testimonial);

    void delete(Long id);

    TestimonialPage findAll(Integer page);
}
