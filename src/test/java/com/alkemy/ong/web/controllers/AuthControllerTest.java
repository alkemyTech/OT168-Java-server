package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.RoleEntity;
import com.alkemy.ong.data.entities.UserEntity;
import com.alkemy.ong.web.controllers.AuthController.LoginDTO;
import com.alkemy.ong.web.controllers.AuthController.RegistrationDTO;
import com.alkemy.ong.data.repositories.UserRepository;
import com.alkemy.ong.domain.security.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtUtil jwtUtil;

    @Autowired
    ObjectMapper mapper;

    @Test
    void loginSuccess() throws Exception {

        LoginDTO loginDTO = buildLoginDTO();
        Authentication authenticationTest = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        String token = buildToken(loginDTO.getEmail(),"User");

        when(userDetailsService.loadUserByUsername(loginDTO.getEmail())).thenReturn(buildUserDetails("USER",loginDTO.getEmail()));
        when(authenticationManager.authenticate(authenticationTest)).thenReturn(authenticationTest);
        when(jwtUtil.generateToken(buildUserDetails("USER",loginDTO.getEmail()))).thenReturn(token);

        mockMvc.perform(post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt",is(token)));
    }

    private RegistrationDTO buildRegistrationDTO(Long id) {
        return null;
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
                .roleEntity(buildRole(1l,roleName))
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
