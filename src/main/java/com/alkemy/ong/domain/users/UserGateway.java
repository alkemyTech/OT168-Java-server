package com.alkemy.ong.domain.users;

import java.util.List;

public interface UserGateway {
    List<User> findAll();
	User findById(Long id);
	User findByEmail(String email);
	void emailExists(String email);
	User register(User user);	
	User update (User user);
	void deleteById(Long id);
}
