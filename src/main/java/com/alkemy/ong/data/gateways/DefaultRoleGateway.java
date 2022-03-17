package com.alkemy.ong.data.gateways;

import com.alkemy.ong.domain.RoleGateway;
import org.springframework.stereotype.Component;

@Component
public class DefaultRoleGateway implements RoleGateway {

    private final RoleGateway roleGateway;

    public DefaultRoleGateway(RoleGateway roleGateway){
        this.roleGateway=roleGateway;
    }
}
