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

import com.alkemy.ong.domain.category.Category;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Entity(name = "categories")
@EqualsAndHashCode(of = { "name", "description", "image" })
@Getter
@NoArgsConstructor
@Setter
@SQLDelete(sql = "UPDATE news SET deleted = false WHERE id = ?")
@Table(name = "categories")
@ToString(of = { "name", "description", "image" })
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
	@Column(name = "createdat")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updateat")
	private LocalDateTime updateAt;

	private Boolean deleted;

	public CategoryEntity() {
		super();
	}

	public CategoryEntity(Long id, String name, String description, String image, LocalDateTime createdAt,
			LocalDateTime updateAt, Boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
		this.deleted = deleted;
	}

	public static CategoryEntity from(Category category) {
		return new CategoryEntity(
				category.getId(), 
				category.getName(), 
				category.getDescription(), 
				category.getImage(),
				category.getCreatedAt(), 
				category.getUpdateAt(), 
				category.getDeleted());
	}

	public Category fromThis() {
		return new Category(
				id, 
				name, 
				description, 
				image, 
				createdAt, 
				updateAt, 
				deleted);
	}

}