package com.alkemy.ong.data.gateways;

import com.alkemy.ong.domain.usecases.UserGateway;
import org.springframework.stereotype.Component;

@Component
public class UserGatewayImpl implements UserGateway {

    private final UserGateway userGateway;

    public UserGatewayImpl (UserGateway userGateway){
        this.userGateway=userGateway;
    }
}
