package com.alkemy.ong.web.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		categorySlimDTO = categoryService.findAll().stream().map(cat -> toSlimDTO(cat))
				.collect(toList());
		return ResponseEntity.ok(categorySlimDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategoryByID(@PathVariable("id") Long id){
		Category category = categoryService.findById(id);
		CategoryDTO categoryDTO = toDTO(category);
		return ResponseEntity.ok(categoryDTO);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDTO> saveCategory(@Valid @RequestBody CategoryDTO categoryDTO){
		return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(categoryService.save(toModel(categoryDTO))));
	}

	private CategorySlimDTO toSlimDTO(Category category) {
		CategorySlimDTO newCategorySlimDTO = CategorySlimDTO.builder().name(category.getName()).build();
		return newCategorySlimDTO;
	}
	
	private CategoryDTO toDTO(Category category) {
		CategoryDTO newCategoryDTO = CategoryDTO.builder()
				.id(category.getId())
				.name(category.getName())
				.description(category.getDescription())
				.image(category.getImage())
				.createdAt(category.getCreatedAt())
				.updatedAt(category.getUpdatedAt())
				.build();
		return newCategoryDTO;
	}
	
	private Category toModel(CategoryDTO categoryDTO) {
		Category newCategory = Category.builder()
				.id(categoryDTO.getId())
				.name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .image(categoryDTO.getImage())
				.createdAt(categoryDTO.getCreatedAt())
				.updatedAt(categoryDTO.getUpdatedAt())
				.build();
		return newCategory;
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
	@NotEmpty(message = "The name field is required.")
	@Pattern(regexp = "[a-zA-Z]{0,255}", message = "The name field must not have numbers.")
	private String name;
	private String description;
	private String image;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
