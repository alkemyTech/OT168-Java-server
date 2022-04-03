package com.alkemy.ong.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.*;

import com.alkemy.ong.domain.security.jwt.AunthenticationResponse;
import com.alkemy.ong.domain.security.jwt.JwtUtil;
import com.alkemy.ong.domain.exceptions.WebRequestException;
import com.alkemy.ong.domain.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.domain.users.User;

import lombok.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final UserService userService;
	private final JwtUtil jwtUtil;
	
	public AuthController(AuthenticationManager authenticationManager,UserDetailsService userDetailsService,JwtUtil jwtUtil,UserService userService) {
        this.authenticationManager = authenticationManager;
		this.userDetailsService= userDetailsService;
		this.jwtUtil=jwtUtil;
		this.userService=userService;
    }
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) throws Exception{
		Authentication authentication;
		Map<String, Object> response = new HashMap<>();
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (BadCredentialsException e) {
			response.put("messaje", "ok: false");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
		final String jwt = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok().body(new AunthenticationResponse(jwt));
	}

	@PostMapping("/register")
	public ResponseEntity register(@Valid @RequestBody RegistrationDTO registrationDTO) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			validatePassword(registrationDTO.password, registrationDTO.matchingPassword);
			UserDTO user = toDTO(userService.register(toModel(registrationDTO)));
			response.put("User", user);
		} catch (Exception ex) {
			response.put("Error", ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
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

	public static void validatePassword(String pswd1, String pswd2) {
		if(!pswd1.equals(pswd2)){throw new WebRequestException("The passwords don't match.");}
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
