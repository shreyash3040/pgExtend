package com.springserver.auth.user_services;

import com.springserver.auth.model.Role;
import com.springserver.auth.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomUserDetails implements UserDetails {
    private User user;
    private List<Role> roles;

    CustomUserDetails(){}
    CustomUserDetails(User user,List<Role>roles){
        this.user=user;
        this.roles=roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        for(Role role:roles)
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
