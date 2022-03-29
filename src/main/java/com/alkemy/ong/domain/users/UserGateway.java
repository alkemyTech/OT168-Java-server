package com.alkemy.ong.domain.users;

public interface UserGateway {
	
	User findByEmail(String email);
}
