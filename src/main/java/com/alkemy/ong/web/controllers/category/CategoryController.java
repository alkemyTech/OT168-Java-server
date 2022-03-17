package com.alkemy.ong.web.controllers.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.domain.usecases.category.Category;
import com.alkemy.ong.domain.usecases.category.CategoryService;
import com.alkemy.ong.web.controllers.category.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public List<CategoryDTO> getAllCategories(){
		return categoryService.findAll()
				.stream()
				.map(Category::fromThis)
				.collect(Collectors.toList());				
	}
	
}
