package com.ecommerceapp.ecommerceserver.controller.service;

import com.ecommerceapp.ecommerceserver.model.entity.User;
import com.ecommerceapp.ecommerceserver.model.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        SimpleGrantedAuthority[] roles = {new SimpleGrantedAuthority(user.getRole())};
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Arrays.asList(roles));
    }
}
