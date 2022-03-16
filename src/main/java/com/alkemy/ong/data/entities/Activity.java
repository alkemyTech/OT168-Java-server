package com.alkemy.ong.data.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String image;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime creation;
    private Boolean status;

    public Activity() {
    }

    public Activity(Long id, String name, String content, String image, LocalDateTime creation, Boolean status) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.image = image;
        this.creation = creation;
        this.status = status;
    }
}
