package com.alkemy.ong.domain.organization;

import com.alkemy.ong.data.entities.SlidesEntity;
import com.alkemy.ong.domain.slides.Slides;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Organization {
    private long idOrganization;
    private String name;
    private String image;
    private String address;
    private Long phone;
    private String email;
    private String about_us_text;
    private String welcome_text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean deleted;


    @Override
    public String toString() {
        return "Organization{" +
                "idOrganization=" + idOrganization +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", address='" + address + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", about_us_text='" + about_us_text + '\'' +
                ", welcome_text='" + welcome_text + '\'' +
                '}';
    }
}
