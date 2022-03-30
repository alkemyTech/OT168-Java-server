package com.alkemy.ong.domain.users;

public interface UserGateway {
	
	User findByEmail(String email);
	boolean checkEmail(String email);
	User register(User user);
}
