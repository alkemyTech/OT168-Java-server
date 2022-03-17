package com.alkemy.ong.web.controllers.category.dto;

import java.time.LocalDateTime;

import com.alkemy.ong.domain.category.Category;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class CategoryDTO {
	private Long id;
	private String name;
	private String description;
	private String image;
	private LocalDateTime createdAt;
	private LocalDateTime updateAt;
	private Boolean deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public CategoryDTO(Long id, String name, String description, String image, LocalDateTime createdAt,
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

	private static CategoryDTO from(Category category) {
		return new CategoryDTO(
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
