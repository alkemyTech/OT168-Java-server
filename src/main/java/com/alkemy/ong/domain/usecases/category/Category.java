package com.alkemy.ong.domain.usecases.category;

import java.time.LocalDateTime;

import com.alkemy.ong.web.controllers.category.dto.CategoryDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Category{

	private Long id;
    private String name;
    private String description;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;	 
	private Boolean deleted;
	
	public Category(String name) {
		super();
		this.name = name;
	}

	public Category(Long id, String name, String description, String image, LocalDateTime createdAt,
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
	
	public static Category from(CategoryDTO categoryDTO) {
		return new Category(
				categoryDTO.getId(), 
				categoryDTO.getName(), 
				categoryDTO.getDescription(), 
				categoryDTO.getImage(),
				categoryDTO.getCreatedAt(), 
				categoryDTO.getUpdateAt(), 
				categoryDTO.getDeleted());
	}

	public CategoryDTO fromThis() {
		return new CategoryDTO(
				id, 
				name, 
				description, 
				image, 
				createdAt, 
				updateAt, 
				deleted);
	}
    
}