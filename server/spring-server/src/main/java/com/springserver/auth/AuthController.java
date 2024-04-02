package com.springserver.auth;

import com.springserver.auth.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) throws Exception{
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                            authRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password",e);
        }
        final UserDetails userDetails=userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token=jwtUtil.generateToken(userDetails);

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority > authorities=userDetails.getAuthorities();
        List<String> authorityNames=new ArrayList<>();
        for(GrantedAuthority authority:authorities)
            authorityNames.add(authority.getAuthority());

        return ResponseEntity.ok(new AuthResponse(token,authorityNames));
    }
}
