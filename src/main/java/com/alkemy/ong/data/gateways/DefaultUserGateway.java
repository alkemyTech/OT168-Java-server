package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.repositories.RoleRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.exceptions.WebRequestException;
import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserGateway;
import com.alkemy.ong.data.entities.UserEntity;
import com.alkemy.ong.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultUserGateway implements UserGateway {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public DefaultUserGateway(UserRepository userRepository, RoleRepository roleRepository,@Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toModel)
                .collect(toList());
    }

    @Override
    public User findByEmail(String email) {
        UserEntity entity = userRepository.findByEmail(email).orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
        return toModel(entity);
    }

    public void emailExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new WebRequestException("Email already exists.");
        }
    }

    public User register(User user) {
        emailExists(user.getEmail());
<<<<<<< HEAD
        user.setRoleId(2L);
=======
        user.setRoleId(2l);
>>>>>>> main
        return toModel(userRepository.save(toEntity(user)));
    }
    
    @Override
    public void deleteById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
        		()-> new ResourceNotFoundException("User not found"));
        userEntity.setDeleted(true);
        userRepository.save(userEntity);
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
                .roleId(userEntity.getRoleEntity().getId())
                .build();
    }

    private UserEntity toEntity(User userModel) {
        return UserEntity.builder().
                firstName(userModel.getFirstName()).
                lastName(userModel.getLastName()).
                email(userModel.getEmail()).
                password(passwordEncoder.encode(userModel.getPassword())).
                roleEntity(roleRepository.findById(userModel.getRoleId()).get()).
                photo(userModel.getPhoto()).
                build();
    }

}
