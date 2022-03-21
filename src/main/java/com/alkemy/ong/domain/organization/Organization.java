package com.alkemy.ong.domain.organization;

import com.alkemy.ong.data.entities.SlidesEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
    //private List<Slides> slides = new ArrayList<>();
    //TODO: Esta lista tiene que traer el slide que es model no el entity y este aún no esta creado!!

}
