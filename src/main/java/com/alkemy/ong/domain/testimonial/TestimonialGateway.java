package com.alkemy.ong.domain.testimonial;

import com.alkemy.ong.data.pagination.PageModel;

public interface TestimonialGateway {

    Testimonial save(Testimonial testimonial);

    Testimonial update(Long id, Testimonial testimonial);

    void delete(Long id);

    PageModel<Testimonial> findAll(int pageNumber);
}
