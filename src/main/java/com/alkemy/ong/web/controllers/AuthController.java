package com.alkemy.ong.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserService;

import static com.alkemy.ong.web.utils.WebUtils.*;

import lombok.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	
	public AuthController(UserService userService, AuthenticationManager authenticationManager) {
		this.userService = userService;
        this.authenticationManager = authenticationManager;
    }
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) throws Exception{
		Authentication authentication;
		UserDTO user;
		Map<String, Object> response = new HashMap<>();
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			user = toDTO(userService.findByEmail(loginDTO.getEmail()));
			response.put("User", user);
		} catch (BadCredentialsException e) {
			response.put("messaje", "ok: false");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity register(@Valid @RequestBody RegistrationDTO registrationDTO, HttpServletRequest request) throws Exception {
		validatePassword(registrationDTO.password, registrationDTO.matchingPassword);
		Map<String, Object> response = new HashMap<>();
		if (!userService.checkEmail(registrationDTO.email)) {
			UserDTO user = toDTO(userService.register(toModel(registrationDTO)));
			response.put("User", user);
		} else {
			response.put("Error", "Email already exists.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	private UserDTO toDTO(User user) {
		return UserDTO.builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.password(user.getPassword())
				.photo(user.getPhoto())
				.build();
	}

	private User toModel(RegistrationDTO user) {
		return User.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.password(user.getPassword())
				.photo(user.getPhoto())
				.build();
	}
	
	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	private static class LoginDTO {
		@Email(message = "This field must be an email")
		@NotEmpty(message = "The email field is required.")
		private String email;
		@NotEmpty(message = "The password field is required.")
		private String password;	
	}
	
	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	private static class UserDTO {
		private Long id;
	    private String firstName;
	    private String lastName;
		private String email;
		private String password;
	    private String photo;
	}

	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	private static class RegistrationDTO {

		@NotEmpty(message = "The 'name' field is required.")
		private String firstName;

		@NotEmpty(message = "The 'last name' field is required.")
		private String lastName;

		@Email(message = "This field must be an email.")
		@NotEmpty(message = "The 'email' field is required.")
		private String email;

		@NotEmpty(message = "The 'password' field is required.")
		@Size(min = 8, message = "Password must be at least 8 characters long.")
		private String password;
		private String matchingPassword;
		private String photo;
	}

}
