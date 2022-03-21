package com.alkemy.ong.web.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.domain.category.Category;
import com.alkemy.ong.domain.category.CategoryService;

import static java.util.stream.Collectors.toList;

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

	private CategorySlimDTO categoryToCategorySlimDTO(Category category) {
		CategorySlimDTO newCategorySlimDTO = CategorySlimDTO.builder().name(category.getName()).build();
		return newCategorySlimDTO;
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
