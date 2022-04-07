package com.alkemy.ong.web.controllers;


import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.*;

import com.alkemy.ong.domain.exceptions.WebRequestException;
import com.alkemy.ong.domain.security.jwt.AunthenticationResponse;
import com.alkemy.ong.domain.security.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserService;

import lombok.*;

@Tag(name = "Authentication")
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

	@Operation(summary = "Login with email and password")
	@ApiResponses( value = {
			@ApiResponse(responseCode = "200", description = "User logged in successfully", content = { @Content( schema = @Schema(implementation = LoginDTO.class))}),
			@ApiResponse(responseCode = "404", description = "NOT FOUND", content = { @Content( schema = @Schema(implementation = LoginDTO.class),
					examples = @ExampleObject(value = "User not found"))}),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST", content = { @Content( schema = @Schema(implementation = LoginDTO.class),
					examples = @ExampleObject(value = "\"The email field is required.\" Or \"The password field is required.\" Or \"This field must be an email\" Or \"ok: false\"\n" +
							"}")
					)
				}
			)
	})
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

	@GetMapping("/me")
	public ResponseEntity<UserDTO> getAuthenticatedUserDetails(@RequestHeader(value = "Authorization") String authorizationHeader) {
		String email = jwtUtil.extractEmail(authorizationHeader);
		UserDTO useDTO = toDTO(userService.findByEmail(email));
		return ResponseEntity.ok().body(useDTO);
	}


	@Operation(summary = "New user registration")
	@ApiResponses( value = {
			@ApiResponse(responseCode = "201", description = "User created successfully",  content = { @Content( schema = @Schema(implementation = UserDTO.class))}),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST", content = { @Content( schema = @Schema(implementation = RegistrationDTO.class),
					examples = @ExampleObject(value = "\"The 'last name' field is required.\" Or \"The 'last name' field is required.\" Or \"The 'email' field is required.\" " +
									"Or \"Password must be at least 8 characters long.\" Or \"This field must be an email.\" Or \"The passwords don't match.\" " +
									"Or \"Email already exists.\"")
			)
		}
	)
})
	@PostMapping("/register")
	public ResponseEntity register(@Valid @RequestBody RegistrationDTO registrationDTO) {
		Map<String, Object> response = new HashMap<>();
		try {
			validatePassword(registrationDTO.password, registrationDTO.matchingPassword);
			UserDTO user = toDTO(userService.register(toModel(registrationDTO)));
		} catch (Exception ex) {
			response.put("Error", ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(registrationDTO.email, registrationDTO.password)
		);
		SecurityContextHolder.getContext().setAuthentication(auth);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(registrationDTO.email);
		final String jwt = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok().body(new AunthenticationResponse(jwt));
	}
	
	private UserDTO toDTO(User user) {
		return UserDTO.builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
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
	@Schema(description = "Attributes required to login")
	private static class LoginDTO {
		@Schema(required = true, example = "juanperez@gmail.com")
		@Email(message = "This field must be an email")
		@NotEmpty(message = "The email field is required.")
		private String email;
		@Schema(example = "passwordExample")
		@NotEmpty(message = "The password field is required.")
		private String password;	
	}

	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(description = "User attributes")
	private static class UserDTO {
		@Schema(required = true, example = "1")
		private Long id;
		@Schema(required = true, example = "Juan")
		private String firstName;
		@Schema(required = true, example = "Perez")
		private String lastName;
		@Schema(required = true, example = "juanperez@gmail.com")
		private String email;
		@Schema(example = "http://photoExample.com")
		private String photo;
	}

	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(description = "User attributes to register")
	private static class RegistrationDTO {

		@NotEmpty(message = "The 'name' field is required.")
		@Schema(required = true, example = "Juan")
		private String firstName;
		@NotEmpty(message = "The 'last name' field is required.")
		@Schema(required = true, example = "Perez")
		private String lastName;
		@Email(message = "This field must be an email.")
		@NotEmpty(message = "The 'email' field is required.")
		@Schema(required = true, example = "juanperez@gmail.com")
		private String email;
		@NotEmpty(message = "The 'password' field is required.")
		@Size(min = 8, message = "Password must be at least 8 characters long.")
		@Schema(required = true, example = "passwordExample")
		private String password;
		@Schema(required = true, example = "passwordExample")
		private String matchingPassword;
		@Schema(example = "http://photoExample.com")
		private String photo;
	}
}
