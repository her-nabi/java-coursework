package ru.abdullaeva.javacoursework.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.abdullaeva.javacoursework.model.auth.Users;
import ru.abdullaeva.javacoursework.security.jwt.JwtUser;
import ru.abdullaeva.javacoursework.security.jwt.JwtUserFactory;
import ru.abdullaeva.javacoursework.service.interf.UsersService;

@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UsersService usersService;

    public JwtUserDetailsService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Users user = usersService.findByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("User with login: " + login + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);

        log.info("IN loadUserByUsername - user with login: {} successfully loaded", login);

        return jwtUser;
    }
}
