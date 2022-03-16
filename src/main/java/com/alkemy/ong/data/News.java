package com.alkemy.ong.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE news SET status = false WHERE newsId = ?")
@AllArgsConstructor
@NoArgsConstructor

public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String content;

    @NotNull
    @Column(nullable = false)
    private String image;

    @NotNull
    @OneToOne
    @Column(nullable = false)
    private Category category;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    private Boolean status;

}