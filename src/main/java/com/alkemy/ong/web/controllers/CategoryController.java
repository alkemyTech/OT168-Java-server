package com.alkemy.ong.web.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.domain.category.Category;
import com.alkemy.ong.domain.category.CategoryService;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;

import lombok.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public ResponseEntity<List<CategorySlimDTO>> getAllCategories() {
		List<CategorySlimDTO> categorySlimDTO;
		categorySlimDTO = categoryService.findAll().stream().map(cat -> categoryToCategorySlimDTO(cat))
				.collect(toList());
		return ResponseEntity.ok(categorySlimDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategoryByID(@PathVariable("id") Long id){
		Category category = categoryService.findById(id);
		CategoryDTO categoryDTO = categoryToCategoryDTO(category);
		return ResponseEntity.ok(categoryDTO);
	}

	private CategorySlimDTO categoryToCategorySlimDTO(Category category) {
		CategorySlimDTO newCategorySlimDTO = CategorySlimDTO.builder().name(category.getName()).build();
		return newCategorySlimDTO;
	}
	
	private CategoryDTO categoryToCategoryDTO(Category category) {
		CategoryDTO newCategoryDTO = CategoryDTO.builder()
				.id(category.getId())
				.name(category.getName())
				.description(category.getDescription())
				.image(category.getImage())
				.createdAt(category.getCreatedAt())
				.updatedAt(category.getUpdatedAt())
				.deleted(category.getDeleted())
				.build();
		return newCategoryDTO;
	}

}

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class CategorySlimDTO {
	private String name;
}

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class CategoryDTO {
	private Long id;
	private String name;
	private String description;
	private String image;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Boolean deleted;
}
