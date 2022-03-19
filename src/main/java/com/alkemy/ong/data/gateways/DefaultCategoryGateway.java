package com.alkemy.ong.data.gateways;

import java.util.List;
import static java.util.stream.Collectors.toList;

import org.springframework.stereotype.Component;

import com.alkemy.ong.data.entities.CategoryEntity;
import com.alkemy.ong.data.repositories.CategoryRepository;
import com.alkemy.ong.domain.category.Category;
import com.alkemy.ong.domain.category.CategoryGateway;

@Component
public class DefaultCategoryGateway implements CategoryGateway {

	private final CategoryRepository categoryRepository;

	public DefaultCategoryGateway(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Category> findAll() {
		List<Category> categories;
		categories = categoryRepository.findAll().stream().map(cat -> categoryEntityToCategory(cat)).collect(toList());
		return categories;
	}

	public static Category categoryEntityToCategory(CategoryEntity categoryEntity) {
		Category newCategory = Category.builder().id(categoryEntity.getId()).name(categoryEntity.getName())
				.description(categoryEntity.getDescription()).createdAt(categoryEntity.getCreatedAt())
				.updatedAt(categoryEntity.getUpdatedAt()).deleted(categoryEntity.getDeleted()).build();
		return newCategory;
	}

	public static CategoryEntity categoryToCategoryEntity(Category category) {
		CategoryEntity newCategoryEntity = CategoryEntity.builder().id(category.getId()).name(category.getName())
				.description(category.getDescription()).createdAt(category.getCreatedAt())
				.updatedAt(category.getUpdatedAt()).deleted(category.getDeleted()).build();
		return newCategoryEntity;
	}

}
