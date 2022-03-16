package com.alkemy.ong.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String message;
    private LocalDateTime deletedAt;

    public Contact() {
    }

    public Contact(Long id, String name, String phone, String email, String message, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.message = message;
        this.deletedAt = deletedAt;
    }
}
