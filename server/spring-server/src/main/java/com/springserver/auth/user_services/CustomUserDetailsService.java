package com.springserver.auth.user_services;

import com.springserver.auth.model.Role;
import com.springserver.auth.model.User;
import com.springserver.auth.repositories.RoleRepository;
import com.springserver.auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser=userRepository.findById(username);
        List<Role> roles=roleRepository.findByUsername(username);
        return new CustomUserDetails(optionalUser.get(),roles);
    }
}
