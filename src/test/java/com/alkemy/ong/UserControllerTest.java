package com.alkemy.ong;

import com.alkemy.ong.data.entities.RoleEntity;
import com.alkemy.ong.data.entities.UserEntity;
import com.alkemy.ong.data.repositories.RoleRepository;
import com.alkemy.ong.data.repositories.UserRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.security.jwt.JwtUtil;
import com.alkemy.ong.web.controllers.UserController.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        UserEntity userEntity = toEntityTest();
        UserDTO userDTO = toDTOTest();

        userDTO.setId(1l);

        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));

        String token = generateToken();

        mockMvc.perform(get("/users/{id}",1l).header("Authorization",token)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO))
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("james@mail.com")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.firstName",is("James")))
                .andExpect(jsonPath("$.lastName",is("Potter")))
                .andExpect(jsonPath("$.photo",is("james.jpg")))
                .andExpect(jsonPath("$.createdAt",is("2022-03-29 18:58:56")))
                .andExpect(jsonPath("$.updatedAt",is("2022-03-29 18:58:56")))
                .andExpect(jsonPath("$.roleId",is(1)));

    }

    private UserDTO toDTOTest() {
        return UserDTO.builder()
                .firstName("James")
                .lastName("Potter")
                .email("james@mail.com")
                .password("12345678")
                .photo("james.jpg")
                .createdAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .updatedAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .roleId(1l)
                .build();
    }

    private UserEntity toEntityTest(){
        return UserEntity.builder()
                .id(1l)
                .firstName("James")
                .lastName("Potter")
                .email("james@mail.com")
                .password("12345678")
                .photo("james.jpg")
                .createdAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .updatedAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .roleEntity(toRoleEntityTest())
                .build();
    }

    private RoleEntity toRoleEntityTest(){
        return RoleEntity.builder()
                .id(1l)
                .name("USER")
                .description("Normal user of the system")
                .createdAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .updatedAt(LocalDateTime.of(2022,03,29,18,58,56,555))
                .build();
    }

    private String generateToken(){
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        UserDetails userDetails = new User("james@mail.com", "admin", Collections.singletonList(authority));
        return jwtUtil.generateToken(userDetails);
    }
}
