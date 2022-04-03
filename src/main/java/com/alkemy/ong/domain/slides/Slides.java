package com.alkemy.ong.domain.slides;

import com.alkemy.ong.domain.organization.Organization;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Slides {
    private Long idSlides;
    private String imageUrl;
    private String text;
    private Integer order;
    private Organization organization;

    @Override
    public String toString() {
        return "Slides{" +
                "idSlides=" + idSlides +
                ", imageUrl='" + imageUrl + '\'' +
                ", text='" + text + '\'' +
                ", order=" + order +
                '}';
    }
}