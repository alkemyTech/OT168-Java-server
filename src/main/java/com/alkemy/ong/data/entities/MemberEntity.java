package com.alkemy.ong.data.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE members SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
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

    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberEntity)) return false;
        MemberEntity entity = (MemberEntity) o;
        return Objects.equals(getId(), entity.getId())
                && Objects.equals(getName(), entity.getName())
                && Objects.equals(getFacebookUrl(), entity.getFacebookUrl())
                && Objects.equals(getInstagramUrl(), entity.getInstagramUrl())
                && Objects.equals(getLinkedinUrl(), entity.getLinkedinUrl())
                && Objects.equals(getImage(), entity.getImage())
                && Objects.equals(getDescription(), entity.getDescription());
    }
}

