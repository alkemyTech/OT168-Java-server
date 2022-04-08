package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.RoleEntity;
import com.alkemy.ong.data.entities.UserEntity;
import com.alkemy.ong.data.repositories.RoleRepository;
import com.alkemy.ong.data.repositories.UserRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.security.jwt.JwtUtil;
import com.alkemy.ong.web.controllers.UserController.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleRepository roleRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Test
    @WithMockUser(roles = "USER")
    void findByIdSuccess() throws Exception {
        UserEntity userEntity = buildEntity(1l,"USER");
        UserDTO userDTO = buildDTO(1l);

        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(Optional.of(userEntity));

        String token = buildToken(userEntity,"USER");

        mockMvc.perform(get("/users/{id}",1l).header("Authorization",token)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.firstName",is("James")))
                .andExpect(jsonPath("$.lastName",is("Potter")))
                .andExpect(jsonPath("$.photo",is("james.jpg")))
                .andExpect(jsonPath("$.createdAt",is("2022-03-29 18:58:56")))
                .andExpect(jsonPath("$.updatedAt",is("2022-03-29 18:58:56")))
                .andExpect(jsonPath("$.roleId",is(1)));
    }

    @Test
    @WithMockUser(roles = "USER")
    void findByIdNotFound() throws Exception {
        UserEntity userEntity = buildEntity(55l,"USER");

        when(userRepository.findById(userEntity.getId())).thenThrow(new ResourceNotFoundException(55l,"User"));
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        String token = buildToken(userEntity,"USER");

        mockMvc.perform(get("/users/{id}",55l).header("Authorization",token))
                .andExpect(status().isNotFound());

        ResourceNotFoundException exceptionThrows = assertThrows(ResourceNotFoundException.class,
                () -> {userRepository.findById(userEntity.getId());}, "No User found with ID "+userEntity.getId());

        Assertions.assertEquals("No User found with ID 55", exceptionThrows.getMessage());
    }

    @Test
    @WithMockUser(roles = "USER")
    void findAllSuccess() throws Exception {
        List<UserEntity> userEntities = asList(buildEntity(1l,"USER"), buildEntity(2l,"USER"), buildEntity(3l,"USER"),buildEntity(4l,"USER"),buildEntity(5l,"USER"));
        List<UserDTO> userDTOS = asList(buildDTO(1l), buildDTO(2l), buildDTO(3l),buildDTO(4l),buildDTO(5l));

        when(userRepository.findAll()).thenReturn(userEntities);

        mockMvc.perform(get("/users")
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(userDTOS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(5)))
                .andExpect(jsonPath("$.[0].id",is(1)))
                .andExpect(jsonPath("$.[1].id",is(2)))
                .andExpect(jsonPath("$.[2].id",is(3)))
                .andExpect(jsonPath("$.[3].id",is(4)))
                .andExpect(jsonPath("$.[4].id",is(5)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteSuccess() throws Exception {

        UserEntity userEntity = buildEntity(1l,"ADMIN");

        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));

        mockMvc.perform(delete("/users/{id}",1l))
                .andExpect(status().isNoContent());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteNotFound() throws Exception {
        UserEntity userEntity = buildEntity(22l,"ADMIN");
        when(userRepository.findById(userEntity.getId())).thenThrow(new ResourceNotFoundException(userEntity.getId(),"User"));

        mockMvc.perform(delete("/users/{id}",1l))
                .andExpect(status().isNotFound());

        ResourceNotFoundException exceptionThrows = assertThrows(ResourceNotFoundException.class,
                () -> {userRepository.findById(userEntity.getId());}, "No User found with ID "+ userEntity.getId());

        Assertions.assertEquals("No User found with ID "+userEntity.getId(), exceptionThrows.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateSuccess() throws Exception{
        UserEntity userEntity = buildEntity(1l,"ADMIN");
        UserDTO userDTO = buildDTO(1l);
        RoleEntity roleEntity = buildRole(1l,"ADMIN");

        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        when(roleRepository.findById(roleEntity.getId())).thenReturn(Optional.of(roleEntity));

        mockMvc.perform(put("/users/{id}",1)
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.firstName",is("James")))
                .andExpect(jsonPath("$.lastName",is("Potter")))
                .andExpect(jsonPath("$.email",is("james@gmail.com")))
                .andExpect(jsonPath("$.photo",is("james.jpg")))
                .andExpect(jsonPath("$.createdAt",is("2022-03-29 18:58:56")))
                .andExpect(jsonPath("$.updatedAt",is("2022-03-29 18:58:56")))
                .andExpect(jsonPath("$.roleId",is(1)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateNotFound() throws Exception{
        UserEntity userEntity = buildEntity(55l,"ADMIN");
        UserDTO userDTO = buildDTO(55l);
        RoleEntity roleEntity = buildRole(1l,"ADMIN");

        when(userRepository.findById(userEntity.getId())).thenThrow(new ResourceNotFoundException(userEntity.getId(),"User"));

        when(roleRepository.findById(roleEntity.getId())).thenReturn(Optional.of(roleEntity));

        mockMvc.perform(put("/users/{id}",55)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO)))
                .andExpect(status().isNotFound());

        ResourceNotFoundException exceptionThrows = assertThrows(ResourceNotFoundException.class,
                () -> {userRepository.findById(userEntity.getId());}, "No User found with ID "+ userEntity.getId());

        Assertions.assertEquals("No User found with ID "+userEntity.getId(), exceptionThrows.getMessage());
    }

    private UserDTO buildDTO(Long id) {
        return UserDTO.builder()
                .id(id)
                .firstName("James")
                .lastName("Potter")
                .email("james@gmail.com")
                .password("12345678")
                .photo("james.jpg")
                .createdAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .updatedAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .roleId(1l)
                .build();
    }

    private UserEntity buildEntity(Long id,String role){
        return UserEntity.builder()
                .id(id)
                .firstName("James")
                .lastName("Potter")
                .email("james@gmail.com")
                .password("12345678")
                .photo("james.jpg")
                .createdAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .updatedAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .roleEntity(buildRole(1l,role))
                .build();
    }

    private RoleEntity buildRole(Long id, String role){
        return RoleEntity.builder()
                .id(id)
                .name(role)
                .description("Normal user of the system")
                .createdAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .updatedAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .build();
    }

    private String buildToken(UserEntity userEntity, String role ){
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+role);
        UserDetails userDetails = new User(userRepository.save(userEntity).getEmail(), "12345678", Collections.singletonList(authority));
        return jwtUtil.generateToken(userDetails);
    }
}
