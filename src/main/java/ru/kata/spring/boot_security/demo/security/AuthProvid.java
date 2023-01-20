package ru.kata.spring.boot_security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.Collections;

@Component
public class AuthProvid implements AuthenticationProvider {
    private UserService userService;

    public AuthProvid(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails userDetails = (UserDetails) userService.loadUserByUsername(username);
        String password = authentication.getCredentials().toString();
        if (!password.equals(userDetails.getPassword()))
            throw new BadCredentialsException("Не верный пароль");
        return new UsernamePasswordAuthenticationToken(userDetails, password,
                Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
