package com.alkemy.ong.data.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "organizations")
@SQLDelete(sql = "UPDATE organization SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class OrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_organization")
    private Long idOrganization;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Long phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "welcome_text")
    private String welcomeText;

    @Column(nullable = false, name = "about_us_text")
    private String aboutUsText;

    private Boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private List<SlidesEntity> slides = new ArrayList<>();

}