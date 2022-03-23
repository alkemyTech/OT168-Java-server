package com.alkemy.ong.domain.category;

import java.util.List;

public interface CategoryGateway {

	List<Category> findAll();
	
	Category findById(Long id);
	
	Category save(Category category);

}
