package com.alkemy.ong.data.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE news SET deleted = true WHERE news_id = ?")
@Where(clause = "deleted = false")
@Table(name = "news", schema = "alkemy_ong")
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long newsId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String image;

    @CreationTimestamp
    @Column(name = "created_at")
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

    private String type;

    @OneToMany(mappedBy = "newsEntity")
    @JsonIgnore
    private List<CommentEntity> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsEntity that = (NewsEntity) o;
        return Objects.equals(newsId, that.newsId) && Objects.equals(name, that.name) && Objects.equals(content, that.content) && Objects.equals(image, that.image) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(deleted, that.deleted) && Objects.equals(type, that.type) && Objects.equals(comments, that.comments);
    }
}