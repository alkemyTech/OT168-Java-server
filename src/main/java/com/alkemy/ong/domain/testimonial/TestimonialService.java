package com.alkemy.ong.domain.testimonial;

import com.alkemy.ong.data.pagination.PageModel;
import org.springframework.stereotype.Service;

@Service
public class TestimonialService {

    private final TestimonialGateway testimonialGateway;

    public TestimonialService(TestimonialGateway testimonialGateway){
        this.testimonialGateway = testimonialGateway;
    }

    public Testimonial save(Testimonial testimonial){return testimonialGateway.save(testimonial);}

    public Testimonial update(Long id, Testimonial testimonial){ return testimonialGateway.update(id, testimonial);}

    public void delete(Long id){testimonialGateway.delete(id);}

    public PageModel<Testimonial> findAll(int pageNumber){return testimonialGateway.findAll(pageNumber);}
}
