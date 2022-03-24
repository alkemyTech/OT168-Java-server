package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.RoleEntity;
import com.alkemy.ong.domain.roles.Role;
import com.alkemy.ong.domain.roles.RoleGateway;
import com.alkemy.ong.data.repositories.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class DefaultRoleGateway implements RoleGateway {

    private final RoleRepository roleRepository;

    public DefaultRoleGateway(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }

    static Role toModel(RoleEntity roleEntity) {
        return Role.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .description(roleEntity.getDescription())
                .createdAt(roleEntity.getCreatedAt())
                .updatedAt(roleEntity.getUpdatedAt())
                .deleted(roleEntity.getDeleted())
                .build();
    }

    static RoleEntity toEntity(Role role) {
        return RoleEntity.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .deleted(role.getDeleted())
                .build();
    }
}
