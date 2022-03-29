package com.alkemy.ong.domain.category;

import org.springframework.stereotype.Service;

import com.alkemy.ong.data.pagination.PageModel;

@Service
public class CategoryService {

	private final CategoryGateway categoryGateway;

	public CategoryService(CategoryGateway categoryGateway) {
		this.categoryGateway = categoryGateway;
	}

	public PageModel<Category> findAll(int pageNumber) {
		return categoryGateway.findAll(pageNumber);
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
