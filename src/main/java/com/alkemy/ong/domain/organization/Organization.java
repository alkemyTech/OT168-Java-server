package com.alkemy.ong.domain.organization;

import com.alkemy.ong.data.entities.SlidesEntity;
import com.alkemy.ong.domain.slides.Slides;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Organization {
    private long idOrganization;
    private String name;
    private String image;
    private String address;
    private Long phone;
    private String email;
    private String aboutUsText;
    private String welcomeText;
    private String facebookUrl;
    private String linkedinUrl;
    private String instagramUrl;
    private SlidesEntity slidesEntity;

    @Override
    public String toString() {
        return "Organization{" +
                "idOrganization=" + idOrganization +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", address='" + address + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", about_us_text='" + aboutUsText + '\'' +
                ", welcome_text='" + welcomeText + '\'' +
                '}';
    }
}