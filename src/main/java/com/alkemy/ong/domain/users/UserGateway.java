package com.alkemy.ong.domain.users;

import java.util.List;

public interface UserGateway {
    List<User> findAll();
	
	User findByEmail(String email);
	void emailExists(String email);
	User register(User user);
	void deleteById(Long id);
}
