package com.alkemy.ong.data.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "Update deleted = true WHERE id = ?")
@Table(name = "members", schema = "alkemy_ong")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "facebookurl")
    private String facebookUrl;

    @Column(name = "instagramurl")
    private String instagramUrl;

    @Column(name = "linkedinurl")
    private String linkedinUrl;

    @Column(nullable = false)
    private String image;

    private String description;

    @CreationTimestamp
    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedat")
    private LocalDateTime updatedAt;

    private Boolean deleted;
}

