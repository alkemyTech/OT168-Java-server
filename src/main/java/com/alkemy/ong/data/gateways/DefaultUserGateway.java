package com.alkemy.ong.data.gateways;

import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserGateway;
import com.alkemy.ong.data.entities.UserEntity;
import com.alkemy.ong.data.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserGateway implements UserGateway {

    private final UserRepository userRepository;

    public DefaultUserGateway(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public User findByEmail(String email) {
        UserEntity entity = userRepository.findByEmail(email).orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
        return toModel(entity);
    }
    
    private User toModel(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .photo(userEntity.getPhoto())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .role(userEntity.getRoleEntity().getId())
                .build();
    }
}
