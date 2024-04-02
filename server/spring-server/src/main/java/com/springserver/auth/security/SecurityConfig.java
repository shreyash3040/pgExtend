package com.springserver.auth.security;

import com.springserver.auth.filters.JwtRequestFilter;
import com.springserver.auth.user_services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    public final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService){
        this.customUserDetailsService=customUserDetailsService;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer->
                configurer.anyRequest().permitAll());
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf->csrf.disable());
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

}

