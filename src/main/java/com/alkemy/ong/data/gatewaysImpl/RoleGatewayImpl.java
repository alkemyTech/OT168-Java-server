package com.alkemy.ong.data.gatewaysImpl;

import com.alkemy.ong.domain.usecases.RoleGateway;

public class RoleGatewayImpl implements RoleGateway {

    private final RoleGateway roleGateway;

    public RoleGatewayImpl (RoleGateway roleGateway){
        this.roleGateway=roleGateway;
    }
}
