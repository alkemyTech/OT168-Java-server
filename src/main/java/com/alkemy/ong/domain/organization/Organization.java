package com.alkemy.ong.domain.organization;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

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
                ", about_us_text='" + aboutUsText + '\'' +
                ", welcome_text='" + welcomeText + '\'' +
                '}';
    }
}
