package com.alkemy.ong.data.gateways;

import com.alkemy.ong.domain.users.UserGateway;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserGateway implements UserGateway {

    private final UserGateway userGateway;

    public DefaultUserGateway(UserGateway userGateway){
        this.userGateway=userGateway;
    }
}
