package com.alkemy.ong.data.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "testimonials")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE testimonials SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class TestimonialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String image;

    private String content;

    @CreationTimestamp
    @Column(name = "created_at")
    @JsonFormat(pattern="dd-MM-yyyy hh:mm")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonFormat(pattern="dd-MM-yyyy hh:mm")
    private LocalDateTime updatedAt;

    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestimonialEntity that = (TestimonialEntity) o;
        return Objects.equals(id,that.id)
                && Objects.equals(name,that.name)
                && Objects.equals(image,that.image)
                && Objects.equals(content,that.content)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt)
                && Objects.equals(deleted, that.deleted);
    }

}
