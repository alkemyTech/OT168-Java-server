package com.alkemy.ong.web.controllers.category.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.alkemy.ong.domain.usecases.category.Category;

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
	
	public static List<CategoryDTO> from(List<Category> categories){
		return categories
				.parallelStream()
				.map(CategoryDTO::from)
				.collect(Collectors.toList());		
	}
}
