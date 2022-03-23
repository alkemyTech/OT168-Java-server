package com.alkemy.ong.domain.category;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {

	private final CategoryGateway categoryGateway;

	public CategoryService(CategoryGateway categoryGateway) {
		this.categoryGateway = categoryGateway;
	}

	public List<Category> findAll() {
		return categoryGateway.findAll();
	}
	
	public Category findById(Long id) {
		return categoryGateway.findById(id);
	}
	
	public Category save(Category category) {
		return categoryGateway.save(category);
	}
	
	public Category update(Long id, Category category) {
		return categoryGateway.update(id, category);
	}

	public void delete(Long id) {
		categoryGateway.delete(id);;
	}

}
