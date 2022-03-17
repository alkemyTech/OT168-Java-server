package com.alkemy.ong.data.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Entity(name = "categories")
@EqualsAndHashCode(of = {"name", "description", "image"})
@Getter
@NoArgsConstructor
@Setter
@SQLDelete(sql = "UPDATE news SET deleted = false WHERE id = ?")
@Table(name = "categories")
@ToString(of = {"name", "description", "image"})
public class CategoryEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	 @Column(nullable = false)
	 private String name;
	 
	 @Column(nullable = true)
	 private String description;
	 
	 @Column(nullable = true)
	 private String image;
	 
	 @CreationTimestamp
	 private LocalDateTime createdAt;

	 @UpdateTimestamp
	 private LocalDateTime updateAt;
	 
	 private Boolean deleted;
	 
}