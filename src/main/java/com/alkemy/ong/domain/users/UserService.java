package com.alkemy.ong.domain.users;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	
	private final UserGateway userGateway;

	public UserService(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public List<User> findAll(){
        return userGateway.findAll();
    }

	public User findByEmail(String email) {
        return userGateway.findByEmail(email);
    }

    public User register(User user) {
        return userGateway.register(user); }

    public User update (User user){
        return userGateway.update(user);
    }

    public User findById(Long id){
        return userGateway.findById(id);
    }
    
    public void deleteById(Long id) {
        userGateway.deleteById(id);
    }
}
