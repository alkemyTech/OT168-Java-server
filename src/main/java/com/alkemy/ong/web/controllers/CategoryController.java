package com.alkemy.ong.web.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.data.pagination.PageMapper;
import com.alkemy.ong.domain.category.Category;
import com.alkemy.ong.domain.category.CategoryService;
import com.alkemy.ong.web.utils.PageDTO;
import com.alkemy.ong.web.utils.WebUtils;

import java.time.LocalDateTime;

import lombok.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;
	private final PageMapper<CategorySlimDTO, Category> pageMapper;

	public CategoryController(CategoryService categoryService, PageMapper bodyMapper) {
		this.categoryService = categoryService;
		this.pageMapper = bodyMapper;
	}

	@GetMapping
	public ResponseEntity<PageDTO<CategorySlimDTO>> getAllCategories(@RequestParam("page") int numberPage) {
		WebUtils.validatePageNumber(numberPage);
        return ResponseEntity.ok().body(pageMapper.toPageDTO(categoryService.findAll(numberPage),CategorySlimDTO.class));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategoryByID(@PathVariable("id") Long id) {
		Category category = categoryService.findById(id);
		CategoryDTO categoryDTO = toDTO(category);
		return ResponseEntity.ok(categoryDTO);
	}

	@PostMapping
	public ResponseEntity<CategoryDTO> saveCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(categoryService.save(toModel(categoryDTO))));
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
			@Valid @RequestBody CategoryDTO categoryDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(toDTO(categoryService.update(id, toModel(categoryDTO))));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
		categoryService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	private CategorySlimDTO toSlimDTO(Category category) {
		CategorySlimDTO newCategorySlimDTO = CategorySlimDTO.builder().name(category.getName()).build();
		return newCategorySlimDTO;
	}

	private CategoryDTO toDTO(Category category) {
		return CategoryDTO.builder()
				.id(category.getId())
				.name(category.getName())
				.description(category.getDescription())
				.image(category.getImage())
				.createdAt(category.getCreatedAt())
				.updatedAt(category.getUpdatedAt())
				.deleted(category.getDeleted())
				.build();
	}

	private Category toModel(CategoryDTO categoryDTO) {
		return Category.builder()
				.id(categoryDTO.getId())
				.name(categoryDTO.getName())
				.description(categoryDTO.getDescription())
				.image(categoryDTO.getImage())
				.createdAt(categoryDTO.getCreatedAt())
				.updatedAt(categoryDTO.getUpdatedAt())
				.deleted(categoryDTO.getDeleted())
				.build();
	}
	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	private static class CategorySlimDTO {
		private String name;
	}

	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	private static class CategoryDTO {
		private Long id;
		@NotEmpty(message = "The name field is required.")
		@Pattern(regexp = "[a-zA-Z]{0,255}", message = "The name field must not have numbers.")
		private String name;
		private String description;
		private String image;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
		private Boolean deleted;
	}
}


