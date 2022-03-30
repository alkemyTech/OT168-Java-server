package com.alkemy.ong.domain.users;

import java.util.List;

public interface UserGateway {
    List<User> findAll();
	
	User findByEmail(String email);
}
