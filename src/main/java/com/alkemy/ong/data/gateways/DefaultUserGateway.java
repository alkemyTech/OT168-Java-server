package com.alkemy.ong.data.gateways;

import com.alkemy.ong.domain.users.UserGateway;
import com.alkemy.ong.data.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserGateway implements UserGateway {

    private final UserRepository userRepository;


    public DefaultUserGateway(UserRepository userRepository){
        this.userRepository=userRepository;
    }
}
