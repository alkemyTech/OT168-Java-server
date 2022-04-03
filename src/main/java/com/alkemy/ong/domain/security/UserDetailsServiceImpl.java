package com.alkemy.ong.domain.security;

import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService service;

    public UserDetailsServiceImpl(UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	User user = service.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(getAuthority(user));
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        }
    }

    private GrantedAuthority getAuthority(User user) {

        if (user.getRoleId() == 1L) {
            return new SimpleGrantedAuthority("ROLE_ADMIN");
        }
        if (user.getRoleId() == 2L) {
            return new SimpleGrantedAuthority("ROLE_USER");
        }
        return null;
    }
}