package com.alkemy.ong.domain.category;

import com.alkemy.ong.data.pagination.PageModel;

public interface CategoryGateway {

	PageModel<Category> findAll(int pageNumber);
	
	Category findById(Long id);
	
	Category save(Category category);
	
	Category update(Long id, Category category);

	void delete(Long id);

}
