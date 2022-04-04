package com.alkemy.ong.data.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Table(name = "users", schema = "alkemy_ong")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "firstname")
    private String firstName;

    @Column(nullable = false, name = "lastname")
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String photo;

    @CreationTimestamp
    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedat")
    private LocalDateTime updatedAt;

    @Builder.Default

    private Boolean deleted = Boolean.FALSE;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity roleEntity;
}