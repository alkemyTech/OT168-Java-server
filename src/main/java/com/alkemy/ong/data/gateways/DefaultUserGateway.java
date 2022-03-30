package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.repositories.RoleRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserGateway;
import com.alkemy.ong.data.entities.UserEntity;
import com.alkemy.ong.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserGateway implements UserGateway {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public DefaultUserGateway(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByEmail(String email) {
        UserEntity entity = userRepository.findByEmail(email).orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
        return toModel(entity);
    }

    public boolean checkEmail(String email) {
        Boolean exists = false;
        if (userRepository.findByEmail(email).isPresent()) {
            exists = true;
        }
        return exists;
    }

    public User register(User user) {
        if (userRepository.findAll().size() == 0) {
            user.setRole(1L);
        } else {
            user.setRole(2L);
        }
        return toModel(userRepository.save(toEntity(user)));
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

    private UserEntity toEntity(User userModel) {
        return UserEntity.builder().
                firstName(userModel.getFirstName()).
                lastName(userModel.getLastName()).
                email(userModel.getEmail()).
                password(passwordEncoder.encode(userModel.getPassword())).
                roleEntity(roleRepository.findById(userModel.getRole()).get()).
                photo(userModel.getPhoto()).
                build();
    }
}
