package com.springserver.auth.filters;

import com.springserver.auth.user_services.CustomUserDetailsService;
import com.springserver.auth.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = null;
        String username = null;
        String authorizationHeader = request.getHeader("Authorization");
        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
                jwt=authorizationHeader.substring(7);
                username=jwtUtil.extractUsername(jwt);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=customUserDetailsService.loadUserByUsername(username);
            if(jwtUtil.validateToken(jwt,userDetails)){
                UsernamePasswordAuthenticationToken
                        usernamePasswordAuthenticationToken=
                        new UsernamePasswordAuthenticationToken(
                                userDetails,null,userDetails.getAuthorities()
                        );
                usernamePasswordAuthenticationToken.setDetails(new
                        WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
