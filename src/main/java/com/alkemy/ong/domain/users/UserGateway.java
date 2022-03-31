package com.alkemy.ong.domain.users;

public interface UserGateway {
	
	User findByEmail(String email);
	void emailExists(String email);
	User register(User user);
}
