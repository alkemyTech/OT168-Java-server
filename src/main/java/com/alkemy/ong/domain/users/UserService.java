package com.alkemy.ong.domain.users;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private final UserGateway userGateway;
	
	public UserService(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

	public User findByEmail(String email) {
        return userGateway.findByEmail(email);
    }

    public User register(User user) { return userGateway.register(user); }
}
