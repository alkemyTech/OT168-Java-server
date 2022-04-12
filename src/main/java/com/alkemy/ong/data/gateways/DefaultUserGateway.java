package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.RoleEntity;
import com.alkemy.ong.data.pagination.PageModel;
import com.alkemy.ong.data.pagination.PageModelMapper;
import com.alkemy.ong.data.repositories.RoleRepository;
import com.alkemy.ong.data.utils.PaginationUtils;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.exceptions.WebRequestException;
import com.alkemy.ong.domain.security.jwt.JwtUtil;
import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserGateway;
import com.alkemy.ong.data.entities.UserEntity;
import com.alkemy.ong.data.repositories.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.alkemy.ong.data.utils.PaginationUtils.DEFAULT_PAGE_SIZE;
import static com.alkemy.ong.data.utils.PaginationUtils.setPagesNumbers;
import static java.util.stream.Collectors.toList;

@Component
public class DefaultUserGateway implements UserGateway {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	private final JwtUtil jwtUtil;
	private final PageModelMapper<User,UserEntity> pageMapper;

	public DefaultUserGateway(UserRepository userRepository, RoleRepository roleRepository,
							  @Lazy PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
							  PageModelMapper<User,UserEntity> pageMapper) {

		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.pageMapper=pageMapper;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll().stream().map(this::toModel).collect(toList());
	}

	@Override
	public User findById(Long id) {
		return toModel(
				userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id,"User")));
	}

	@Override
	public User findByEmail(String email) {
		UserEntity entity = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return toModel(entity);
	}

	public void emailExists(String email) {
		if (userRepository.findByEmail(email).isPresent()) {
			throw new WebRequestException("Email already exists.");
		}
	}

	public User register(User user) {
		emailExists(user.getEmail());
		user.setRoleId(2l);
		return toModel(userRepository.save(toEntity(user)));
	}

	@Override
	public User update(User user) {
		roleRepository.findById(user.getRoleId())
				.orElseThrow(() -> new ResourceNotFoundException(user.getRoleId(), "Role"));
		UserEntity userEntity = toEntity(findById(user.getId()));
		return toModel(userRepository.save(toUpdate(userEntity, user)));
	}

	@Override
	public void deleteById(Long id) {
		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id,"User"));
		userEntity.setDeleted(true);
		userRepository.save(userEntity);
	}

	@Override
	public PageModel<User> findAll(int pageNumber) {
		return pageMapper.toPageModel(setPagesNumbers(userRepository
				.findAll(PageRequest.of(pageNumber,DEFAULT_PAGE_SIZE)),"/users?page="),User.class);
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
		return UserEntity.builder()
				.id(userModel.getId())
				.firstName(userModel.getFirstName())
				.lastName(userModel.getLastName())
				.email(userModel.getEmail())
				.password(passwordEncoder.encode(userModel.getPassword()))
				.roleEntity(roleRepository.findById(userModel.getRoleId()).get())
				.photo(userModel.getPhoto())
				.createdAt(userModel.getCreatedAt()).build();
	}

	private UserEntity toUpdate(UserEntity entity, User userModel){
        entity.setFirstName(userModel.getFirstName());
        entity.setLastName(userModel.getLastName());
        entity.setEmail(userModel.getEmail());
        entity.setPassword(passwordEncoder.encode(userModel.getPassword()));
        entity.setPhoto(userModel.getPhoto());
        entity.setRoleEntity(roleRepository.findById(userModel.getRoleId()).get());

        return entity;
    }
}
