package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.RoleEntity;
import com.alkemy.ong.data.entities.UserEntity;
import com.alkemy.ong.data.repositories.RoleRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.web.controllers.AuthController.LoginDTO;
import com.alkemy.ong.web.controllers.AuthController.RegistrationDTO;
import com.alkemy.ong.web.controllers.AuthController.UserDTO;
import com.alkemy.ong.data.repositories.UserRepository;
import com.alkemy.ong.domain.security.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    JwtUtil jwtUtil;

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    RoleRepository roleRepository;

    @Test
    void loginSuccess() throws Exception {

        LoginDTO loginDTO = buildLoginDTO();
        usernamePasswordAuthenticationToken.setDetails(loginDTO);

        when(userDetailsService.loadUserByUsername(loginDTO.getEmail())).thenReturn(buildUserDetails("USER",loginDTO.getEmail()));
        when(usernamePasswordAuthenticationToken.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(any());

        mockMvc.perform(post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("jwt",is(buildToken(loginDTO.getEmail(),"USER"))));
    }

    @Test
    void loginNotFound() throws Exception {

        LoginDTO loginDTO = buildLoginDTO();

        when(userDetailsService.loadUserByUsername(loginDTO.getEmail())).thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

    @Test
    void registerSuccess() throws Exception {
        UserEntity entityRequest = builUserdEntity(null,"USER");
        UserEntity entityResponse = builUserdEntity(1l,"USER");
        RoleEntity roleEntity = buildRole(2l,"USER");
        RegistrationDTO registrationDTO = buildRegistrationDTO();


        when(userRepository.save(entityRequest)).thenReturn(entityResponse);
        when(userRepository.findByEmail(entityRequest.getEmail())).thenReturn(Optional.empty());
        when(roleRepository.findById(roleEntity.getId())).thenReturn(Optional.of(roleEntity));
        when(userDetailsService.loadUserByUsername(registrationDTO.getEmail())).thenReturn(buildUserDetails("USER",registrationDTO.getEmail()));
        when(usernamePasswordAuthenticationToken.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(any());


        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.firstName",is("James")))
                .andExpect(jsonPath("$.lastName",is("Potter")))
                .andExpect(jsonPath("$.email",is("james@gmail.com")))
                .andExpect(jsonPath("$.photo",is("james.jpg")));
    }

    @Test
    void registerBadRequestEmail() throws Exception{
        UserEntity entityRequest = builUserdEntity(null,"USER");
        UserEntity entityResponse = builUserdEntity(1l,"USER");
        RegistrationDTO registrationDTO = buildRegistrationDTO();

        when(userRepository.findByEmail(entityRequest.getEmail())).thenReturn(Optional.of(entityResponse));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email already exists."));
    }

    @Test
    void registerBadRequestPasswordMatch() throws Exception{
        RegistrationDTO registrationDTO = buildRegistrationDTO();

        registrationDTO.setMatchingPassword("abcdefghi");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The passwords don't match."));
    }

    @Test
    void registerBadRequestRegisterField() throws Exception{
        RegistrationDTO registrationDTO = buildRegistrationDTO();

        registrationDTO.setFirstName("");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0]",is("The 'name' field is required.")));
    }

    @Test
    void meSuccess() throws Exception {
        UserDTO userDTO = buildUserDTO(1l);
        UserEntity userEntity = builUserdEntity(1l,"USER");

        String token = buildToken(userDTO.getEmail(),"USER");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(userEntity));
        when(usernamePasswordAuthenticationToken.isAuthenticated()).thenReturn(true);

        mockMvc.perform(get("/auth/me").header("Authorization",token)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.firstName",is("James")))
                .andExpect(jsonPath("$.lastName",is("Potter")))
                .andExpect(jsonPath("$.email",is("james@gmail.com")))
                .andExpect(jsonPath("$.photo",is("james.jpg")));
    }

    private RegistrationDTO buildRegistrationDTO() {
        return RegistrationDTO.builder()
                .firstName("James")
                .lastName("Potter")
                .email("james@gmail.com")
                .password("12345678")
                .matchingPassword("12345678")
                .photo("james.jpg")
                .build();
    }

    private LoginDTO buildLoginDTO(){
        return LoginDTO.builder()
                .email("james@gmail.com")
                .password("12345678")
                .build();
    }

    private UserEntity builUserdEntity(Long id,String roleName){
        return UserEntity.builder()
                .id(id)
                .firstName("James")
                .lastName("Potter")
                .email("james@gmail.com")
                .password("12345678")
                .photo("james.jpg")
                .createdAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .updatedAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .roleEntity(buildRole(2l,roleName))
                .build();
    }

    private UserDTO buildUserDTO(Long id) {
        return UserDTO.builder()
                .id(id)
                .firstName("James")
                .lastName("Potter")
                .email("james@gmail.com")
                .photo("james.jpg")
                .build();
    }

    private RoleEntity buildRole(Long id, String roleName){
        return RoleEntity.builder()
                .id(id)
                .name(roleName)
                .description("Normal user of the system")
                .createdAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .updatedAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .build();
    }

    private UserDetails buildUserDetails(String roleName,String userName){
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+roleName);
        return  new User(userName, "12345678", Collections.singletonList(authority));
    }

    private String buildToken(String email, String roleName ){
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+roleName);
        UserDetails userDetails = new User(email, "12345678", Collections.singletonList(authority));
        return jwtUtil.generateToken(userDetails);
    }
}
