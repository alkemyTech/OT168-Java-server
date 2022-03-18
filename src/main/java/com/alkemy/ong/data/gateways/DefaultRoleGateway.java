package com.alkemy.ong.data.gateways;

import com.alkemy.ong.domain.roles.RoleGateway;
import com.alkemy.ong.data.repositories.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class DefaultRoleGateway implements RoleGateway {

    private final RoleRepository roleRepository;

    public DefaultRoleGateway(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }
}
